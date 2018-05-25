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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.beust.jcommander.JCommander;

/**
 * 
 * Main Class to run this program, parses command line options
 * 
 * @author pratapi.patel
 *
 */
@SpringBootApplication
public class JwtCrackerApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(CommandLineOptions.class);

    @Autowired
    private ThreadService threadService;
     
    @SuppressWarnings("squid:S2629")
    @Override
    public void run(String... args) throws Exception {

        CommandLineOptions options = new CommandLineOptions();

        // parsing command line
        new JCommander(options, args);

        LOG.info("************************************************");
        LOG.info("charset [{}]", new String(options.charSet()));
        LOG.info("threads [{}]", options.maxThread());
        LOG.info("max password length [{}]", options.maxKeyLength());

        StopWatch watch = new StopWatch();
        watch.start();
        try {
            String password = threadService.crackJwt(options);
            LOG.info("password cracked: [{}]", password);
        } catch (ExecutionException e) {

            LOG.info("Password couldn't be cracked, Possible Reasons");
            LOG.info("1. It contains characters other than the given charset");
            LOG.info("2. Its length exceeds the given max key length");
            LOG.info("3. {} caused by {}", e, e.getCause());
        } catch (Exception e) {
            LOG.warn("something unexpected happened", e);
        } finally {
            watch.stop();
            LOG.info("total time taken [hh::mm:ss:SSS] {}", watch);
            LOG.info("************************************************");
        }
    }
    
    private void unused() {
    	
    }

    public static void main(String[] args) {

        SpringApplication.run(JwtCrackerApplication.class, args).close();
        System.exit(0);
    }
}
