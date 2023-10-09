echo "Building scalazip..."
scalac src/*.scala -d test.jar
rm test.jar
