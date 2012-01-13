/*
 * Copyright 2002-2012 SCOOP Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.scoopgmbh.orchestration;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2011-10-12T09:59:54.824+02:00
 * Generated source version: 2.4.2
 * 
 */
@WebServiceClient(name = "OrchestrationService", 
                  wsdlLocation = "file:wsdl/Orchestration.wsdl",
                  targetNamespace = "http://orchestration.scoopgmbh.de/") 
public class OrchestrationService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://orchestration.scoopgmbh.de/", "OrchestrationService");
    public final static QName OrchestrationServicePort = new QName("http://orchestration.scoopgmbh.de/", "OrchestrationServicePort");
    static {
        URL url = null;
        try {
            url = new URL("file:wsdl/Orchestration.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(OrchestrationService_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:wsdl/Orchestration.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public OrchestrationService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public OrchestrationService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OrchestrationService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns OrchestrationService
     */
    @WebEndpoint(name = "OrchestrationServicePort")
    public OrchestrationService getOrchestrationServicePort() {
        return super.getPort(OrchestrationServicePort, OrchestrationService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns OrchestrationService
     */
    @WebEndpoint(name = "OrchestrationServicePort")
    public OrchestrationService getOrchestrationServicePort(WebServiceFeature... features) {
        return super.getPort(OrchestrationServicePort, OrchestrationService.class, features);
    }

}
