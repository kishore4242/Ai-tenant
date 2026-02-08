#!/bin/bash
# To install the ollama model to machine

MODEL="llama3"

echo "Starting to install ollama"

sudo apt install curl
echo "curl install successfully"

if ! curl -fsSL https://ollama.com/install.sh | sh; then
    echo "Ollama not installed, try to install manually !!"
    exit 1
fi

echo "($ollama --version)"

echo "Pulling model: $MODEL "

if ! ollama pull "$MODEL" ; then
  echo "try to run it in manually !!"
fi

echo "Running model: $MODEL"
ollama run "$MODEL"