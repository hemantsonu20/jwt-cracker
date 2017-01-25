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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

@SpringBootApplication
public class JwtCrackerApplication implements CommandLineRunner {

    @Autowired
    private ThreadService threadService;

    @Override
    public void run(String... args) throws Exception {

        CommandLineOptions options = new CommandLineOptions();

        new JCommander(options, args);

        System.out.printf("******* cracking jwt token *******%ntoken [%s]%nthreads [%d]%nmax password length [%d]%n",
                options.token(), options.maxThread(), options.maxKeyLength());

        threadService.crackJwt(options);
    }

    public static void main(String[] args) throws Exception {

        SpringApplication.run(JwtCrackerApplication.class, args).close();
        System.exit(0);
    }

    static class CommandLineOptions {

        public static final String SMALL_ALPHA = "abcdefghijklmnoprstuqvwxyz";
        public static final String CAP_ALPHA = "ABCDEFGHIJKLMNOPRSTUQVWXYZ";
        public static final String NUM = "0123456789";
        
        @Parameter(names = { "-t", "--token" }, description = "jwt token to be cracked", required = true)
        private String token;

        @Parameter(names = { "-m", "--threads" }, description = "max no of threads to be used, default is 20")
        private int maxThread = 20;

        @Parameter(names = { "-l", "--length" }, description = "max possible length of the jwt secret key, upto this length key will be generated")
        private int maxKeyLength = 10;
        
        @Parameter(names = { "-c", "--chars" }, description = "charset to be included, combination of a-z, A-Z and 0-9 only, default a-zA-Z0-9")
        private String charSet = "a-zA-Z0-9";

        public String token() {

            return token;
        }

        public int maxThread() {

            return maxThread;
        }

        public int maxKeyLength() {

            return maxKeyLength;
        }
        
        public char[] charSet() {
         
            if(StringUtils.isBlank(charSet)) {
                throw new ParameterException("char set is empty");
            }
            
            String output = "";
            if(charSet.contains("a-z")) {
                output += SMALL_ALPHA;
            }
            
            if(charSet.contains("A-Z")) {
                output += CAP_ALPHA;
            }
            
            if(charSet.contains("0-9")) {
                output += NUM;
            }
            
            if(StringUtils.isBlank(output)) {
                throw new ParameterException("charset is invalid");
            }
            
            return output.toCharArray();
        }
    }
}
