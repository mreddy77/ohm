How to build this project
=========================
1. Checkout this project to a directory of you choice
2. Make sure you have tomcat installed on your box.
Please configure the below properties to 

cargo {
     	containerId = 'tomcat8x'
		port = 8082
	
	deployable {		
		file = file('<project root>/build/libs/testIOhm.war')  // for example /home/mv664/workspace/testIOhm/build/libs/testIOhm.war
		context = 'testIOhm'
	}

	local {
	  	homeDir = file('<tomcat installation folder>') // for example /home/mv664/work/installed/apache-tomcat-8.0.26
		outputFile = file('<any dir of your choice>/output.log')
		timeout = 300000

		containerProperties {
			property 'cargo.tomcat.ajp.port', 9099		    
		    
		}
		systemProperties {
		    property 'spring.profiles.active', 'staging'	
		    property 'log4j.level', 'DEBUG'	
		    property 'app.config.dir', '<project root>/src/main/resources' // for example /home/mv664/workspace/testIOhm/src/main/resources   
	     
		}
	}
}

3.  use gradle to build/deploy this project

Gradle options
cargoRedeployLocal - would build and deploy
build - would just build

4. Once deployed, the app should be available at the below url

http://localhost:8082/testIOhm


