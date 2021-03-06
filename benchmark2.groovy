println "We start"
g = Neo4jFactory.open('conf/neo4j-server.properties')

file = new File(".", 'benchmark_result.txt')

startTime = new Date()

file << "start time $startTime.time\n"
file << "\n------------------------------------------------\n"
file << "starting Q1: Get all nodes with energy <= 0.0f \n"
startQ1 = new Date()
q1 = g.V.has('energy', T.lte, 0.0f).count()
endQ1 = new Date()
file << "duration Q1: ${endQ1.time - startQ1.time}ms size: ${q1}"


file << "\n------------------------------------------------\n"
file << "starting Q2: Get all nodes with energy > 0.0f \n"
startQ2 = new Date()
q2 = g.V.has('energy', T.gt, 0.0f).count()
endQ2 = new Date()
file << "duration Q2: ${endQ2.time - startQ2.time}ms size: ${q2}\n"

file << "\n------------------------------------------------\n"
file << "starting Q3: Get the input node of the nodes with\n" +
        "- number_of_atoms > 3 \n" +
        "- energy > 0\n"
startQ3 = new Date()
q3 = g.V.has('number_of_atoms', T.gt, 3).has('energy', T.gt, 0.0f).in.dedup().count()
endQ3 = new Date()
file << "duration Q3: ${endQ3.time - startQ3.time}ms size: ${q3}\n"
file << "duration Q3: ${(endQ3.time - startQ3.time)/1000/60} m size: ${q3}\n"

file << "\n------------------------------------------------\n"
file << "starting Q4: Get the input node and all its attributes of the nodes with\n" +
        "- number_of_atoms > 3 \n" +
        "- energy > 0\n"
startQ4 = new Date()
q4 = g.V.has('number_of_atoms', T.gt, 3).has('energy', T.gt, 0.0f).in.map.dedup().count()
endQ4 = new Date()
file << "duration Q4: ${endQ4.time - startQ4.time}ms size: ${q4}\n"
file << "duration Q4: ${(endQ4.time - startQ4.time)/1000/60} m size: ${q4}\n"



endTime = new Date()
file << "\n------------------------------------------------\n"
file << "end time ${endTime.time}"
duration = endTime.time - startTime.time
file << "duration total ${duration}ms\n"
file << "duration in seconds ${duration/1000} s\n"
file << "duration in minutes ${duration/1000/60} m\n"


file << "===================================================\n"


g.shutdown()
