#!/bin/bash
# Script to run the game as HOST

echo "Starting Kombla Game - HOST MODE"
echo "=================================="
echo "Instructions:"
echo "1. When the game starts, select 'Host Game'"
echo "2. Wait for the client to connect"
echo ""

cd "$(dirname "$0")"
java -cp "target/game-sousse-kombla-0.7.3.jar:target/dependency/*" net.thevpc.gaming.atom.examples.kombla.KomblaGame
