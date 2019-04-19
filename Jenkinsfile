#!/usr/bin/env groovy

def notifyBuild(String buildStatus = 'STARTED') {
    buildStatus = buildStatus?:'SUCCESS'

    def colorName = 'RED'
    def colorCode = '#FF0000'
    def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
    def summary = "${subject} (${env.BUILD_URL})"

    if (buildStatus == 'STARTED') {
        color = 'YELLOW'
        colorCode = '#FFFF00'
    } else if (buildStatus == 'SUCCESS') {
        color = 'GREEN'
        colorCode = '#00FF00'
    } else {
        color = 'RED'
        colorCode = '#FF0000'
    }
    slackSend(color: colorCode, message: summary)
}

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
                script {
                    try {
                        notifyBuild('Started')
                        build job: 'AggregationEngine/ae-test'
                    } catch (e) {
                        currentBuild.result = "FAILED"
                        throw e
                    } finally {
                        notifyBuild(currentBuild.result)
                    }
                }
            }


        }
        stage('Build') {
            steps {
                script {
                    try {
                        notifyBuild('Started')
                        build job: 'AggregationEngine/ae-build'
                    } catch (e) {
                        currentBuild.result = "FAILED"
                        throw e
                    } finally {
                        notifyBuild(currentBuild.result)
                    }
                }
            }
        }
        stage('Release') {
            steps {
                script {
                    try {
                        notifyBuild('Started')
                        build job: 'AggregationEngine/ae-release'
                    } catch (e) {
                        currentBuild.result = "FAILED"
                        throw e
                    } finally {
                        notifyBuild(currentBuild.result)
                    }
                }
            }
        }
        stage('Results') {
            steps {
                echo 'Results publish.'

            }
        }
    }
}