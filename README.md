This is a simple implementation of a blockchain in Java.

## Getting started

Clone this repository
```
git clone https://github.com/sandeeppsunny/Blockchain-implementation-java.gitt
```

Bring up the blockchain nodes
```
docker-compose up
```

This command spins up 3 blockchain nodes. REST API's were implemented in order to interact with the blockchain nodes.
Node-1 is exposed on port 4569
Node-2 is exposed on port 4570
Node-3 is exposed on port 4571

In order to add a node into the blockchain network, it needs to have the ip address the any node in the blockchain network.
Once the new node has the ip address of a node in the blockchain network, the bootstrapping process will traverse
the entire blockchain network and register the ip address of every other node in the blockchain with itself.

Once, this is completed adding a block to any node in the network will result in a distributed consensus and eventually
all nodes in the network will have the same blockchain.

Whenever a new block is added to the blockchain, the mining processs kicks in to find a correct nonce. This mimics Proof of Work.
