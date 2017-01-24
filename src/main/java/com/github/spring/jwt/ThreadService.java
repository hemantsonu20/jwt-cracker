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
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.time.StopWatch;
import org.springframework.stereotype.Service;

import com.github.spring.jwt.JwtCrackerApplication.CommandLineOptions;

/**
 * Class is responsible for decoding the jwt token and starting threads
 * execution to crack the jwt
 * 
 * @author heman
 *
 */
@Service
public class ThreadService {

    public static final String CHARSET = "abcdefghijklmnopqrstuABCDEFGHIJKLMNOPQRSTU0123456789vwxyzVWXYZ";
    public static final char[] CHAR_ARRAY = CHARSET.toCharArray();

    static {
        // shuffle the char array for better performance
        shuffle();
    }

    /**
     * Method to create threads and assign the task to executors
     * 
     * @param options
     */
    public void crackJwt(CommandLineOptions options) {

        // service to verify the token is cracked or not
        JwtService jwtService = new JwtService(options.token());

        // creating lists of task to be executed
        List<Task> tasks = new ArrayList<>(CHAR_ARRAY.length);

        for (int i = 0; i < CHAR_ARRAY.length; i++) {

            tasks.add(new Task(CHAR_ARRAY, i, options.maxKeyLength(), jwtService));
        }
        
        ExecutorService executorService = Executors.newFixedThreadPool(options.maxThread());

        StopWatch watch = new StopWatch();
        watch.start();

        try {
            System.out.printf("password cracked: [%s]%n", executorService.invokeAny(tasks));
        } catch (ExecutionException e) {

            System.out.println("Password couldn't be cracked, Possible Reasons");
            System.out.printf("1. It contains characters other than the charset [%s]%n", CHARSET);
            System.out.printf("2. Its length exceeds the given max key length [%d]%n", options.maxKeyLength());
        } catch (InterruptedException e) {

            System.out.println("something unexpected happened");
            e.printStackTrace();

        } finally {
            watch.stop();
            executorService.shutdownNow();
        }
        System.out.printf("total time taken [hh::mm:ss:SSS] %s%n", watch);
    }

    /**
     * Method to shuffle the character array. It doesn't shuffle the last 10
     * character ("vwxyzVWXYZ") for optimization purpose.
     * 
     * @param chars
     */
    private static void shuffle() {

        Random rnd = ThreadLocalRandom.current();

        for (int i = CHAR_ARRAY.length - 1 - 10; i > 0; i--) {

            int index = rnd.nextInt(i + 1);
            char tmp = CHAR_ARRAY[index];
            CHAR_ARRAY[index] = CHAR_ARRAY[i];
            CHAR_ARRAY[i] = tmp;
        }
        System.out.println(new String(CHAR_ARRAY));
    }
}
