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

import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.beust.jcommander.JCommander;

@SpringBootApplication
public class JwtCrackerApplication implements CommandLineRunner {

    @Autowired
    private ThreadService threadService;

    @Override
    public void run(String... args) throws Exception {

        CommandLineOptions options = new CommandLineOptions();

        // parsing command line
        new JCommander(options, args);

        System.out.printf(
                "token [%s]%ncharset [%s]%nthreads [%d]%nmax password length [%d]%n",
                options.token(),
                new String(options.charSet()),
                options.maxThread(),
                options.maxKeyLength());

        StopWatch watch = new StopWatch();
        watch.start();
        try {
            System.out.printf("password cracked: [%s]%n", threadService.crackJwt(options));
        }
        catch (ExecutionException e) {

            System.out.println("Password couldn't be cracked, Possible Reasons");
            System.out.printf("1. It contains characters other than the given charset");
            System.out.printf("2. Its length exceeds the given max key length");
        }
        catch(Exception e) {
            System.out.println("something unexpected happened");
            e.printStackTrace();
        }
        finally {
            watch.stop();
            System.out.printf("total time taken [hh::mm:ss:SSS] %s%n", watch);
        }
    }

    public static void main(String[] args) throws Exception {

        SpringApplication.run(JwtCrackerApplication.class, args).close();
        System.exit(0);
    }
}
