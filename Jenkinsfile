node {
    stage ('build sources') {
        sh 'gradlew build -Dorg.gradle.jvmargs=-Xmx256m -Dorg.gradle.daemon=false'
    }
}