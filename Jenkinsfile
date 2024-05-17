pipeline {
    agent any

    stages {
        stage('SCM'){
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/Oskarin-koulutehtavat/matrix.git'
            }
        }
        stage('Maven Build') {
            steps {
                // Run Maven on a Unix agent.
                withSonarQubeEnv(installationName: 'sonar', credentialsId: 'SQT_WSL') {
                    withMaven(maven: 'Maven 3.9.6') {
                        sh 'mvn -Dskip.tests clean package sonar:sonar'
                    }
                }
                // To run Maven on a Windows agent, use bat instead of sh
            }
            post {
                // If Maven was able to compile archive the jar file.
                success {
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        stage('Maven Test') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/Oskarin-koulutehtavat/matrix.git'

                // Run Maven tests.
                withMaven(maven: 'Maven 3.9.6') {
                    sh 'mvn test'
                }
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results.
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Docker Build') {
            steps {
                // Build docker image tagged example/matrix:latest using
                // the current directory (.) as a build context
                sh 'docker build -t example/matrix:latest .'
            }
        }
    }
}
