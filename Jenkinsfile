#!/usr/bin/env groovy
BRANCH_NAME = ""

def obtainBranchName(branch) {
    def result
    switch (branch) {
        case 'dev':
            echo "====++++ Development ++++===="
            result = 'develop'
            break
        case 'prod':
            echo "====++++ Production ++++===="
            result = 'master'
            break
        default:
            result = 'develop'
            break
    }
    result
}

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
    parameters {
        choice(
            choices: 'dev\nprod',
            description: 'Environment',
            name: 'environment_argument'
        )
    }
    stages {
        stage('Environment') {
            steps {
                script {
                    BRANCH_NAME = obtainBranchName(params.environment_argument)
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    try {
                        notifyBuild('Started')
                        build job: 'AggregationEngine/ae-test',  parameters: [
                            string(name: 'BRANCH_NAME', value: "$BRANCH_NAME")
                        ]
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
                        build job: 'AggregationEngine/ae-build', parameters: [
                            string(name: 'BRANCH_NAME', value: "$BRANCH_NAME")
                        ]
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
                        build job: 'AggregationEngine/ae-release', parameters: [
                            string(name: 'BRANCH_NAME', value: "$BRANCH_NAME")
                        ]
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
