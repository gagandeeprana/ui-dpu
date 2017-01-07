package com.dpu.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author jagvir
 *
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class Service {
	
	@JsonProperty(value = "service_id")
	private int serviceId;

	@JsonProperty(value = "service_name")
	private String serviceName;

	@JsonProperty(value = "service_response")
	private int serviceResponse;

	@JsonProperty(value = "status")
	private int status;

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getServiceResponse() {
		return serviceResponse;
	}

	public void setServiceResponse(int serviceResponse) {
		this.serviceResponse = serviceResponse;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
