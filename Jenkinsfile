node {
    deleteDir()
    docker.image('lamtev/java').inside {
        stage ('checkout sources') {
            git branch: 'develop', credentialsId: 'feb0f42d-93d1-4d5d-b591-5513c485d91b', url: 'https://github.com/lezhenin/InSpace.git'
        }
        stage ('build sources') {
            sh 'gradle build -Dorg.gradle.jvmargs=-Xmx256m -Dorg.gradle.daemon=false'
        }
        stage ('test') {
            sh 'gradle test'
        }
        stage ('publish results') {
            junit 'build/test-results/TEST*.xml'
        }
    }
}