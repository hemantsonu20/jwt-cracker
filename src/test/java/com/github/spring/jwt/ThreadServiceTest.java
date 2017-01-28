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

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.beust.jcommander.JCommander;

/**
 * @author heman
 *
 */
public class ThreadServiceTest {

    private static final String TOKEN_1 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsImp0aSI6IjNkZWIyZmEzLWU2MWMtNDZkMC05M2IxLWFlYTU2ZWQ3ZTZiYSIsImlhdCI6MTQ4NTU4Nzg5OCwiZXhwIjoxNDg1NTkxNDk4fQ._0IPrjaIaFG6uga1t0YuEvhx37L5gPz7YPL8JGwHi0o";
    private static final String SECRET_1 = "any";

    private static final String TOKEN_2 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsImp0aSI6IjE5YTNkOWY1LTVlNTYtNDViNS04MDdhLWRlZWNkMGI1YTlhOSIsImlhdCI6MTQ4NTU4ODE0OSwiZXhwIjoxNDg1NTkxNzQ5fQ.h_6KsKdMUWxzDM2L3gWLcPe8cLqsQrVZpx8Rnf2fyXc";
    private static final String SECRET_2 = "567";

    private static final String TOKEN_3 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsImp0aSI6IjRmMzAwNTE3LThhYTMtNDMzNi05OWU2LWJmZjljNjZiNzMzZCIsImlhdCI6MTQ4NTU4ODIzNiwiZXhwIjoxNDg1NTkxODM2fQ.erPWUD-nVfOmKnpW72f1SWIngParQWGdFb0C-neteIw";
    private static final String SECRET_3 = "pq68";

    private static final String TOKEN_4 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsImp0aSI6IjI4NzEzNDQ5LWQ1ZDctNDNmMy04MThmLTJlN2IzYTk5ZWVlOSIsImlhdCI6MTQ4NTU4ODMyMiwiZXhwIjoxNDg1NTkxOTIyfQ.xy_kwzJpIS9wdpoygajZShKYrOAQeUx5EhFGLWkofqs";
    private static final String SECRET_4 = "B4u";

    @Test
    public void testToken1() throws InterruptedException, ExecutionException {

        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, "-t", TOKEN_1, "-mt", "5", "-l", "5", "-c", "a-z");

        ThreadService service = new ThreadService();
        assertEquals(SECRET_1, service.crackJwt(options));
    }

    @Test
    public void testToken2() throws InterruptedException, ExecutionException {

        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, "-t", TOKEN_2, "-mt", "5", "-l", "5", "-c", "0-9");

        ThreadService service = new ThreadService();
        assertEquals(SECRET_2, service.crackJwt(options));
    }

    @Test
    public void testToken3() throws InterruptedException, ExecutionException {

        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, "-t", TOKEN_3, "-mt", "5", "-l", "5", "-c", "a-z0-9");

        ThreadService service = new ThreadService();
        assertEquals(SECRET_3, service.crackJwt(options));
    }

    @Test
    public void testToken4() throws InterruptedException, ExecutionException {

        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, "-t", TOKEN_4, "-c", "a-zA-Z0-9");

        ThreadService service = new ThreadService();
        assertEquals(SECRET_4, service.crackJwt(options));
    }

    /**
     * TOKEN_4 secret key is "B4u" but it can't be cracked using charset "0-9", so it
     * will throw ExecutionException
     * 
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test(expected = ExecutionException.class)
    public void testTokenCrackFailure() throws InterruptedException, ExecutionException {

        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, "-t", TOKEN_4, "-mt", "5", "-l", "3", "-c", "0-9");

        ThreadService service = new ThreadService();
        service.crackJwt(options);
    }
}
