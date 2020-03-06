const React = require('react');

const CompLibrary = require('../../core/CompLibrary.js');
const Container = CompLibrary.Container;
const MarkdownBlock = CompLibrary.MarkdownBlock; /* Used to read markdown */

class License extends React.Component {

render() {
     return(
         <Container>
            <h1>Hazelcast Jet License</h1>

            <p>Hazelcast Jet is free and open source software. It's available under one of two licenses:</p>
            <ol>
                <li><code>hazelcast-jet</code> is provided under <a href="https://www.apache.org/licenses/LICENSE-2.0.txt">Apache License, Version 2.0</a></li>
                <li>Plugins and connectors distributed in <code>opt</code> folder are provided under <a href="/license/hazelcast-community-license.txt">Hazelcast Community License</a></li>
            </ol>
        </Container>
     );
    }
}
module.exports = License;