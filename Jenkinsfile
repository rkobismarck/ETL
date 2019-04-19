node (label: 'master') {
   stage('Test') {
     build 'AggregationEngine/ae-test'
   }
   stage('Build') {
     build 'AggregationEngine/ae-build'
   }
   stage('Release') {
     build 'AggregationEngine/ae-release'   
   }
   stage('Results') {
     echo 'Results'
   }
}