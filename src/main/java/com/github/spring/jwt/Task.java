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

    private final char[] buffer;
    private final int startIndex;
    private final int maxKeyLength;
    private final JwtService service;

    public Task(char[] buffer, int startIndex, int keyLength, JwtService service) {

        this.buffer = buffer;
        this.startIndex = startIndex;
        this.maxKeyLength = keyLength;
        this.service = service;
    }

    @Override
    public String call() throws Exception {

        for (int i = 1; i <= maxKeyLength; i++) {

            String secretKey = generate(Character.toString(buffer[startIndex]), i - 1);
            if (null != secretKey) {

                System.out.println("returned " + secretKey);
                return secretKey;
            }
        }
        throw new IllegalArgumentException("password not found starting with char " + buffer[startIndex]);
    }

    public String generate(String current, int length) {

        if (length == 0) {

            //System.out.println("generated " + current);
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

    private String processPassword(String current) {

        if (service.isMatched(current)) {
            
            System.out.printf("found: %s%n", current);
            return current;
        }
        return null;
    }
}
