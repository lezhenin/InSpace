node {
    deleteDir()
    docker.image('lamtev/java').inside {
        stage ('checkout sources') {
            git branch: 'develop', credentialsId: 'feb0f42d-93d1-4d5d-b591-5513c485d91b', url: 'https://github.com/lezhenin/InSpace.git'
        }
        stage ('build sources') {
            sh 'gradle build -Dorg.gradle.jvmargs=-Xmx256m -Dorg.gradle.daemon=false'
        }
        stage ('publish results') {
            sh 'gradle jacocoTestReport -Dorg.gradle.jvmargs=-Xmx256m -Dorg.gradle.daemon=false'
            publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: false, reportDir: 'build/reports/jacoco/test/html', reportFiles: 'index.html', reportName: 'Jacoco HTML Report'])
            publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: false, reportDir: 'build/reports/tests', reportFiles: 'index.html', reportName: 'JUnit HTML Report'])
        }
    }
}