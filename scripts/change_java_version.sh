#!/usr/bin/env bash

# Script to change system variables from one Java version to another 
#   as we're implementing Java11, but have to keep working 
#   on Java8 at the same time. 

# Variables
symlinks_path=/usr/bin
java11_home_path="/home/msanchez/programs/java"

if  [ "$EUID" -ne 0 ]; then # is this root? 
	echo "Please, run as root"
else 
	if [ $# -eq 0 ]; then # are parameters empty? 
		echo "Please input as parameter the Java version you want to change to"
	elif [ $1 = "java11" ]; then 
		echo "Change from Java8 to Java11" 
		echo "Rename symbolic links from Java to Java8..."
		if [ -f $symlinks_path/java11 ]; then # does this file exist? 
			mv $symlinks_path/java $symlinks_path/java8
			mv $symlinks_path/javac $symlinks_path/javac8
			mv $symlinks_path/javadoc $symlinks_path/javadoc8
			mv $symlinks_path/javah $symlinks_path/javah8
			mv $symlinks_path/javap $symlinks_path/javap8
		
			echo "Rename symbolic links from Java11 to Java..."
			mv $symlinks_path/java11 $symlinks_path/java
			mv $symlinks_path/javac11 $symlinks_path/javac
			mv $symlinks_path/javadoc11 $symlinks_path/javadoc
			mv $symlinks_path/javah11 $symlinks_path/javah
			mv $symlinks_path/javap11 $symlinks_path/javap

                        echo "Reset JAVA_HOME value..."
                        export JAVA_HOME=""
			echo "Done"
		else 
			echo "ERROR. Could not find file named java11. Is this the correct version to change to?"
		fi 
	
	elif [ $1 = "java8" ]; then
		echo "Change from Java11 to Java8"
                echo "Rename symbolic links from Java to Java11..."
		if [ -f $symlinks_path/java8 ]; then
	                mv $symlinks_path/java $symlinks_path/java11
	                mv $symlinks_path/javac $symlinks_path/javac11
	                mv $symlinks_path/javadoc $symlinks_path/javadoc11
	                mv $symlinks_path/javah $symlinks_path/javah11
	                mv $symlinks_path/javap $symlinks_path/javap11
	
	                echo "Rename symbolic links from Java8 to Java..."
	                mv $symlinks_path/java8 $symlinks_path/java
	                mv $symlinks_path/javac8 $symlinks_path/javac
	                mv $symlinks_path/javadoc8 $symlinks_path/javadoc
	                mv $symlinks_path/javah8 $symlinks_path/javah
	                mv $symlinks_path/javap8 $symlinks_path/javap

			echo "Change JAVA_HOME value..." 
			export JAVA_HOME=$java11_home_path
	                echo "Done"
		else 
			echo "ERROR. Could not find file named java8. Is this the correct version to change to?"
		fi

	else
		echo "Could not recognize Java version as parameter"
	fi
fi
