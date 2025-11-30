#!/bin/bash
# Script to run the game as CLIENT

echo "Starting Kombla Game - CLIENT MODE"
echo "===================================="
echo "Instructions:"
echo "1. Make sure the HOST is already running"
echo "2. When the game starts, select 'Join Game'"
echo "3. Enter the server address (default: localhost)"
echo ""

cd "$(dirname "$0")"
java -cp "target/game-sousse-kombla-0.7.3.jar:target/dependency/*" net.thevpc.gaming.atom.examples.kombla.KomblaGame
