javac -d target/classes -cp lib/*.jar;src/main/java src/main/java/ProjectApplication.java
javadoc -d docs -sourcepath src/main/java -subpackages entity:login -exclude java.net:java.lang:web
