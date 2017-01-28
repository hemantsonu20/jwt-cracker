#!/bin/bash

export MAVEN_OPTS="-Xmx1G -Xms128m"
MAVEN_OPTIONS="-Dmaven.test.redirectTestOutputToFile=false -Dsurefire.useFile=false -B -e -V"

if [ "$TRAVIS_BRANCH" == "master" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
    echo 'Analyse and trigger QA of master branch'

    # Fetch all commit history so that SonarQube has exact blame information
    # for issue auto-assignment
    # This command can fail with "fatal: --unshallow on a complete repository does not make sense"
    # if there are not enough commits in the Git repository (even if Travis executed git clone --depth 50).
    # For this reason errors are ignored with "|| true"
    git fetch --unshallow || true

    . set_maven_build_version $TRAVIS_BUILD_NUMBER

    mvn org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar $MAVEN_OPTIONS
    
elif [ "$TRAVIS_PULL_REQUEST" != "false" ] && [ -n "${GITHUB_TOKEN:-}" ]; then
    echo 'Internal pull request: trigger QA and analysis'

    . set_maven_build_version $TRAVIS_BUILD_NUMBER

    mvn org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar \
        $MAVEN_OPTIONS \
        -Dsource.skip=true \
        -Dsonar.analysis.mode=preview \
        -Dsonar.github.pullRequest=$TRAVIS_PULL_REQUEST \
        -Dsonar.github.repository=$TRAVIS_REPO_SLUG \
        -Dsonar.github.oauth=$GITHUB_TOKEN \

else
    echo 'Feature branch or external pull request: no QA, no analysis. Skip sources'

    mvn install $MAVEN_OPTIONS -Dsource.skip=true
fi
