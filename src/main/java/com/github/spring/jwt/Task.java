/**
 *   Copyright 2016 Pratapi Hemant Patel
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   
 */
package com.github.spring.jwt;

import java.util.concurrent.Callable;

/**
 * @author heman
 *
 */
public class Task implements Callable<String> {

    // character array from which password will be generated
    private final char[] buffer;

    // this thread will generate password starting with character
    // buffer[startIndex], For each thread it will be unique
    private final int startIndex;

    // maximum length key to be generated, for example if its value is 5, then 1
    // to 5 length keys will be generated
    private final int maxKeyLength;

    // service which checks whether generated key is actual secret key or not
    private final JwtService service;

    public Task(char[] buffer, int startIndex, int keyLength, JwtService service) {

        this.buffer = buffer;
        this.startIndex = startIndex;
        this.maxKeyLength = keyLength;
        this.service = service;
    }

    @Override
    public String call() throws Exception {

        // loop for generating keys of length 1 upto maxKeyLength
        for (int i = 1; i <= maxKeyLength; i++) {

            String secretKey = generate(Character.toString(buffer[startIndex]), i - 1);
            if (null != secretKey) {
                return secretKey;
            }
        }
        // throw exception if unsuccessful
        throw new IllegalArgumentException("password not found starting with char " + buffer[startIndex]);
    }

    /**
     * Method to generate keys of the given length
     * 
     * @param current
     * @param length
     * @return
     */
    public String generate(String current, int length) {

        if (length == 0) {

            // System.out.println("generated " + current);
            return processPassword(current);
        }
        for (int i = 0; i < buffer.length; i++) {

            String tmp = generate(current + buffer[i], length - 1);
            if (null != tmp) {
                return tmp;
            }
        }
        return null;
    }

    /**
     * Method to check generated key is actual secret key or not
     * 
     * @param current
     * @return
     */
    private String processPassword(String current) {

        if (service.isMatched(current)) {

            System.out.printf("found: %s%n", current);
            return current;
        }
        return null;
    }
}
