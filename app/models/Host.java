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
package models;

import java.util.List;

import javax.persistence.Entity;

import play.db.jpa.Model;

/**
 * @author chamerling
 *
 */
@Entity
public class Host extends Model {
	
	public long lastPing;
	
	public long lastSuccess;
	
	public int lastStatus;
	
	public String hostname;

	public static Host getFromName(String hostname) {
		List<Host> hosts =  Host.find("byHostname", hostname).fetch();
		if (hosts != null && hosts.size() > 0) {
			return hosts.get(0);
		}
		return null;
	}

}
