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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

/**
 * Class is responsible for decoding the jwt token and starting threads
 * execution to crack the jwt
 * 
 * @author heman
 *
 */
@Service
public class ThreadService {

    /**
     * Method to create threads and assign the task to executors
     * 
     * @param options
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String crackJwt(CommandLineOptions options) throws InterruptedException, ExecutionException {

        char[] charset = options.charSet();

        // service to verify the token is cracked or not
        JwtService jwtService = new JwtService(options.token());

        // creating lists of task to be executed
        List<Task> tasks = new ArrayList<>(charset.length);

        // tasks are added in a manner that first 1 length all keys should be
        // checked then 2 length keys the 3 length keys and so on.
        for (int length = 1; length <= options.maxKeyLength(); length++) {

            for (int i = 0; i < charset.length; i++) {
                tasks.add(new Task(charset, i, length, jwtService));
            }
        }
     
        ExecutorService executorService = Executors.newFixedThreadPool(options.maxThread());
        try {
            return executorService.invokeAny(tasks);
        } finally {
            executorService.shutdownNow();
        }
    }
}
