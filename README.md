# Inavitas
Inavitas Sample Data Producer

Step 1 : Run Zookeeper on server

Step 2 : Run Kafka on server

Step 3 : Run application jar.

	java -jar Inavitas_SampleData-1.0-SNAPSHOT-shaded.jar

Step 4 : Verify all data is published or not by running kafka api.

	bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic sample-data --from-beginning


Thanks
Mukesh Kumar
	
	
