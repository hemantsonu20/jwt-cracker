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

    private char[] buffer;
    private int startIndex;
    private int keyLength;

    public Task(char[] buffer, int startIndex, int keyLength) {

        this.buffer = buffer;
        this.startIndex = startIndex;
        this.keyLength = keyLength;
    }

    @Override
    public String call() throws Exception {

        generate("" + buffer[startIndex], keyLength - 1);
        return null;
    }

    public void generate(String current, int length) {

        if (length == 0) {
            System.out.printf("%d: %s\n", current);
            return;
        }
        for (int i = 0; i < buffer.length; i++) {
            generate(current + buffer[i], length - 1);
        }
    }
}
