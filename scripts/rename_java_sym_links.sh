#!/usr/bin/env bash
# path where the java symbolic links are located at
path=/home/msanchez/tmp/script

if  [ "$EUID" -ne 0 ]; then # is this root? 
	echo "Please, run as root"
else 
	if [ $# -eq 0 ]; then # are parameters empty? 
		echo "Please input as parameter the Java version you want to change to"
	elif [ $1 = "java11" ]; then 
		echo "Change from Java8 to Java11" 
		echo "Rename symbolic links from Java to Java8..."
		if [ -f $path/java11 ]; then # does this file exist? 
			mv $path/java $path/java8
			mv $path/javac $path/javac8
			mv $path/javadoc $path/javadoc8
			mv $path/javah $path/javah8
			mv $path/javap $path/javap8
		
			echo "Rename symbolic links from Java11 to Java..."
			mv $path/java11 $path/java
			mv $path/javac11 $path/javac
			mv $path/javadoc11 $path/javadoc
			mv $path/javah11 $path/javah
			mv $path/javap11 $path/javap
			echo "Done"
		else 
			echo "ERROR. Could not find file named java11. Is this the correct version to change to?"
		fi 
	
	elif [ $1 = "java8" ]; then
		echo "Change from Java11 to Java8"
                echo "Rename symbolic links from Java to Java11..."
		if [ -f $path/java8 ]; then
	                mv $path/java $path/java11
	                mv $path/javac $path/javac11
	                mv $path/javadoc $path/javadoc11
	                mv $path/javah $path/javah11
	                mv $path/javap $path/javap11
	
	                echo "Rename symbolic links from Java8 to Java..."
	                mv $path/java8 $path/java
	                mv $path/javac8 $path/javac
	                mv $path/javadoc8 $path/javadoc
	                mv $path/javah8 $path/javah
	                mv $path/javap8 $path/javap
	                echo "Done"
		else 
			echo "ERROR. Could not find file named java8. Is this the correct version to change to?"
		fi

	else
		echo "Could not recognize Java version as parameter"
	fi
fi
