/**
 * Copyright 2012 Bas de Nooijer. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of the copyright holder.
 */

/**
 * Solr delay query component
 *
 * This simple component can be used to force a delay in query execution. This can be
 * useful for testing timeouts / failovers / parallel execution in clients.
 *
 * To use the component:
 * - add it to your solrconfig.xml,
 * - place the jar file in the Solr instance libs dir
 * - restart solr
 * - add "&delay=1000" to your querystring. The value is in millisec.
 *
 * If you remove the delay param the component won't slow the query down.
 * IMPORTANT: This component is intended for use in testing only! Enabling this in production environments is not recommended.
 *
 * @author Bas de Nooijer
 * @copyright Copyright 2011 Bas de Nooijer, Raspberry
 * @see <a href="http://www.raspberry.nl">Raspberry</a>
 */
package nl.raspberry.solr;

import java.io.IOException;
import org.apache.solr.handler.component.ResponseBuilder;
import org.apache.solr.handler.component.SearchComponent;

public class DelayComponent extends SearchComponent{

    Integer delay;
    
    @Override
    public void prepare(ResponseBuilder builder) throws IOException {
        delay = builder.req.getParams().getInt("delay");
    }
    
    @Override
    public void process(ResponseBuilder builder) throws IOException {
        if (delay != null && delay > 0) {
            try {
                Thread.sleep(delay.longValue());
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            builder.rsp.add("delay", delay);
        }

    }

    @Override
    public String getDescription() {
        return "Force a delay in query execution (for testing purposes)";
    }

    @Override
    public String getSource() {
        return "";
    }

    @Override
    public String getSourceId() {
        return "";
    }

    @Override
    public String getVersion() {
        return "";
    }
}
