pipeline{
    agent any
     parameters {
        booleanParam(name: 'package', defaultValue: true, description: '打包项目')
        booleanParam(name: 'push', defaultValue: false, description: '推送镜像')
        string(name: 'version', defaultValue: 'v1.0', description: '推送镜像版本')
    }
    tools {
        maven 'apache-maven-3.6.3' 
    }
    stages{
        stage("package"){
            when {
                expression { return params.package } 
            }
            steps{
                sh 'mvn clean package -Dmaven.skip.tests=true'
            }
           
        }
        stage("build images"){
            steps{
                sh 'docker build -t lanjerry-admin .'
                sh 'docker tag lanjerry-admin lanjerry-admin:${version}'
            }
        }
        stage("run"){
            steps{
                pwd
                //sh 'docker-compose -f /opt/lanjerry/docker-compose-admin.yml up -d'
            }
        }
        stage("push"){
            when {
                expression { return params.push } 
            }
             steps{
                sh 'docker tag lanjerry-admin:${version} registry.cn-hangzhou.aliyuncs.com/nieqiurong/lanjerry-admin:${version}'
                sh 'docker push registry.cn-hangzhou.aliyuncs.com/nieqiurong/lanjerry-admin:${version}'
            }
        }
    }
}