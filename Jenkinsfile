pipeline {
    agent any

    stages {
        stage('Maven Build') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/Oskarin-koulutehtavat/matrix.git'

                // Run Maven on a Unix agent.
                withMaven(globalMavenSettingsConfig: '', jdk: '', maven: 'Maven 3.9.6', mavenSettingsConfig: '', traceability: true) {
                    sh 'mvn -D"skip.tests clean package'
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
                withMaven(globalMavenSettingsConfig: '', jdk: '', maven: 'Maven 3.9.6', mavenSettingsConfig: '', traceability: true) {
                    sh 'mvn test'
                }
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results.
                always {
                    junit '/target/surefire-reports/*.xml'
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
