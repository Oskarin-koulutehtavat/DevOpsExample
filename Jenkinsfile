pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {
        stage('Maven Build') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/Oskarin-koulutehtavat/matrix.git'

                // Run Maven on a Unix agent.
                sh "mvn -D"maven.test.failure.ignore"=true -D"checkstyle.failOnViolation"=false clean compile test package"

                // To run Maven on a Windows agent, use bat instead of sh
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        stage('Docker Build') {
            steps {
                // Build docker image tagged example/matrix:latest using the current directory (.) as a build context
                sh 'docker build -t example/matrix:latest .'
            }
        }
    }
}
