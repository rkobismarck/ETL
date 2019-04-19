pipeline {
    agent {
        label 'master'
    }
    triggers {
        pollSCM('')
    }
    stages {
        stage('Test') {
            steps {
                build 'AggregationEngine/ae-test'

            }
        }
        stage('Build') {
            steps {
                build 'AggregationEngine/ae-build'

            }
        }
        stage('Release') {
            steps {
                build 'AggregationEngine/ae-release'

            }
        }
        stage('Results') {
            steps {
                echo 'Results publish.'

            }
        }
    }

}