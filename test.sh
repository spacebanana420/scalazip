echo "Building scalazip..."
scalac src/*.scala test/*.scala -d test.jar && scala test.jar
rm test.jar
