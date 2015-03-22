Rem 更改编码为utf-8
chcp 65001

set classpath=%classpath%;testng-6.8.jar;UI_Fortune_Smoke-1.0.1.jar

java -Dfile.encoding="utf-8" org.testng.TestNG testng.xml

pause