/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.esFacilCore.components;

/**
 *
 * @author luizo
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ResponsesHelper {

	public <T> ResponseEntity<T> buildResponse(T data, HttpStatus status) {
		return new ResponseEntity<T>(data, status);
	}

	public ResponseEntity buildResponse(HttpStatus status) {
		return new ResponseEntity(status);
	}

	public <T> ResponseEntity<T> ok(T data) {
		return buildResponse(data, HttpStatus.OK);
	}

	public ResponseEntity<Success> ok() {
		return buildResponse(new Success(), HttpStatus.OK);
	}

	public ResponseEntity<Void> okNoData() {
		return buildResponse(HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<Void> internalErr() {
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ModelAndView redirectTo(String url, ModelMap params) {
		return new ModelAndView("redirect:" + url, params);
	}

	public ModelAndView redirectTo(String url) {
		return new ModelAndView("redirect:" + url, new ModelMap());
	}

	private class Success {

            boolean success;

            public Success() {
                    success = true;
            }

	}

	public class HealthStatus {

            String status, msg;

            public HealthStatus(String status, String msg) {
                this.status = status;
                this.msg = msg;
            }
            
	}

	public ResponseEntity healthy() {
		HealthStatus healthy = new HealthStatus("UP", "Service OK :-)");
		return ok(healthy);
	}
}
