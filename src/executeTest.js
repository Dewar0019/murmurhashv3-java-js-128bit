// const murmurhash = require('./murmurhashv3_128x64.js');
const murmurhash = require('./murmurhashv3_128x64_WEB.js');

if (process.argv.length < 4) {
    console.log('Usage: node ' + process.argv[1] + ' <inputText.txt>' + ' <outputHash.txt>');
    process.exit(1);
}
// Read the file and print its contents.
const fs = require('fs')
    , inputTextFile = process.argv[2];
fs.readFile(inputTextFile, 'utf8', function(err, data) {
    if (err) throw err;
    console.log('OK: ' + inputTextFile);
    checkHashData(data);
});

const checkHashData = (inputs) => {
    const outputFile = process.argv[3];
    fs.readFile(outputFile, 'utf8', function(err, data) {
        if (err) throw err;
        const javaOutputs = data.split(',').filter(i => i != null);
        const allInputs = inputs.split(',').filter(i => i != null);

        if(javaOutputs.length !== allInputs.length) {
            throw new Error(`Mismatch number of entries from input: ${javaOutputs.length} and output: ${allInputs.length}`)
        }

        for(let i = 0; i < allInputs.length - 1; i++) {
            const jsMurmurhash = murmurhash(allInputs[i]);
            console.log(`java: ${javaOutputs[i]} js: ${jsMurmurhash}  match: ${javaOutputs[i] === jsMurmurhash} length: ${allInputs[i].length}, string: ${allInputs[i]}`);
            if(javaOutputs[i] !== jsMurmurhash) {
                throw new Error(`HASH DID NOT MATCH for ${allInputs[i]}`);
            }
        }

        console.log("**********************************");
        console.log(`Matched a total of: ${allInputs.length}`);
        console.log("**********************************");

    });

};