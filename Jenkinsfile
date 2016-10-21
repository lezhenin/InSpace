node {
    docker.image('lamtev/java').inside {
        stage ('build sources') {
            sh 'gradle build -Dorg.gradle.jvmargs=-Xmx256m -Dorg.gradle.daemon=false'
        }
    }
}