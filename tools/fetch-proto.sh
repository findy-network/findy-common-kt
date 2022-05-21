#!/bin/bash

mkdir -p ./protos/src/main/proto/
curl https://raw.githubusercontent.com/findy-network/findy-agent-api/master/idl/v1/agency.proto > ./protos/src/main/proto/agency.proto
echo 'option java_multiple_files = true;' >> ./protos/src/main/proto/agency.proto
echo 'option java_package = "org.findy_network.findy_common_kt";' >> ./protos/src/main/proto/agency.proto

curl https://raw.githubusercontent.com/findy-network/findy-agent-api/master/idl/v1/agent.proto > ./protos/src/main/proto/agent.proto
echo 'option java_multiple_files = true;' >> ./protos/src/main/proto/agent.proto
echo 'option java_package = "org.findy_network.findy_common_kt";' >> ./protos/src/main/proto/agent.proto

curl https://raw.githubusercontent.com/findy-network/findy-agent-api/master/idl/v1/protocol.proto > ./protos/src/main/proto/protocol.proto
echo 'option java_multiple_files = true;' >> ./protos/src/main/proto/protocol.proto
echo 'option java_package = "org.findy_network.findy_common_kt";' >> ./protos/src/main/proto/protocol.proto

curl https://raw.githubusercontent.com/findy-network/findy-agent-api/master/VERSION > ./protos/src/main/proto/VERSION
