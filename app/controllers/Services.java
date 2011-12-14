/**
 * 
 */
package controllers;

/*
 * Copyright 2011 Christophe Hamerling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import models.Host;
import play.mvc.Controller;

/**
 * Exposed Web services
 * 
 * @author chamerling
 * 
 */
public class Services extends Controller {

	public static void status() {
		System.out.println("Got a status call...");
		Gson gson = new Gson();
		Host newHost = gson.fromJson(new InputStreamReader(request.body), Host.class);
		
		long current = System.currentTimeMillis();
		// get the host or register a new one is needed
		Host host = Host.getFromName(newHost.hostname);
		if (host != null) {
			host.lastPing = current;
			host.lastSuccess = current;
			host.save();
		} else {
			newHost.lastPing = current;
			newHost.lastSuccess = current;
			newHost.save();
		}
	}

}
