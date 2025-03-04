/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.mw.portal.search.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.util.function.Supplier;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
@GraphQLName("SearchResult")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "SearchResult")
public class MWSearchResult extends com.liferay.portal.search.rest.dto.v1_0.SearchResult {
	
	@Schema(description = "The moduleNames field.")
	public String getModuleNames() {
		if (_moduleNamesSupplier != null) {
			moduleNames = _moduleNamesSupplier.get();

			_moduleNamesSupplier = null;
		}

		return moduleNames;
	}

	public void setModuleNames(String moduleNames) {
		this.moduleNames = moduleNames;

		_moduleNamesSupplier = null;
	}

	@JsonIgnore
	public void setModuleNames(
		UnsafeSupplier<String, Exception> moduleNamesUnsafeSupplier) {

		_moduleNamesSupplier = () -> {
			try {
				return moduleNamesUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The moduleNames field.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String moduleNames;

	@JsonIgnore
	private Supplier<String> _moduleNamesSupplier;	
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>	

	@Schema(description = "The moduleCodes field.")
	public String getModuleCodes() {
		if (_moduleCodesSupplier != null) {
			moduleCodes = _moduleCodesSupplier.get();

			_moduleCodesSupplier = null;
		}

		return moduleCodes;
	}

	public void setModuleCodes(String moduleCodes) {
		this.moduleCodes = moduleCodes;

		_moduleCodesSupplier = null;
	}

	@JsonIgnore
	public void setModuleCodes(
		UnsafeSupplier<String, Exception> moduleCodesUnsafeSupplier) {

		_moduleCodesSupplier = () -> {
			try {
				return moduleCodesUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The moduleCodes field.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String moduleCodes;

	@JsonIgnore
	private Supplier<String> _moduleCodesSupplier;	
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	@Schema(description = "The moduleLecturers field.")
	public String getModuleLecturers() {
		if (_moduleLecturersSupplier != null) {
			moduleLecturers = _moduleLecturersSupplier.get();

			_moduleLecturersSupplier = null;
		}

		return moduleLecturers;
	}

	public void setModuleLecturers(String moduleLecturers) {
		this.moduleLecturers = moduleLecturers;

		_moduleLecturersSupplier = null;
	}

	@JsonIgnore
	public void setModuleLecturers(
		UnsafeSupplier<String, Exception> moduleLecturersUnsafeSupplier) {

		_moduleLecturersSupplier = () -> {
			try {
				return moduleLecturersUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The moduleLecturers field.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String moduleLecturers;

	@JsonIgnore
	private Supplier<String> _moduleLecturersSupplier;		
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>




	@Schema(description = "The moduleDepartments field.")
	public String getModuleDepartments() {
		if (_moduleDepartmentsSupplier != null) {
			moduleDepartments = _moduleDepartmentsSupplier.get();

			_moduleDepartmentsSupplier = null;
		}

		return moduleDepartments;
	}

	public void setModuleDepartments(String moduleDepartments) {
		this.moduleDepartments = moduleDepartments;

		_moduleDepartmentsSupplier = null;
	}

	@JsonIgnore
	public void setModuleDepartments(
		UnsafeSupplier<String, Exception> moduleDepartmentsUnsafeSupplier) {

		_moduleDepartmentsSupplier = () -> {
			try {
				return moduleDepartmentsUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The moduleDepartments field.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String moduleDepartments;

	@JsonIgnore
	private Supplier<String> _moduleDepartmentsSupplier;		
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	@Schema(description = "The moduleCredits field.")
	public Long getModuleCredits() {
		if (_moduleCreditsSupplier != null) {
			moduleCredits = _moduleCreditsSupplier.get();

			_moduleCreditsSupplier = null;
		}

		return moduleCredits;
	}

	public void setModuleCredits(Long moduleCredits) {
		this.moduleCredits = moduleCredits;

		_moduleCreditsSupplier = null;
	}

	@JsonIgnore
	public void setModuleCredits(
		UnsafeSupplier<Long, Exception> moduleCreditsUnsafeSupplier) {

		_moduleCreditsSupplier = () -> {
			try {
				return moduleCreditsUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The moduleCredits field.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long moduleCredits;

	@JsonIgnore
	private Supplier<Long> _moduleCreditsSupplier;		
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	@Schema(description = "The modulesOnlineOnly field.")
	public Boolean getModulesOnlineOnly() {
		if (_modulesOnlineOnlySupplier != null) {
			modulesOnlineOnly = _modulesOnlineOnlySupplier.get();

			_modulesOnlineOnlySupplier = null;
		}

		return modulesOnlineOnly;
	}

	public void setModulesOnlineOnly(Boolean modulesOnlineOnly) {
		this.modulesOnlineOnly = modulesOnlineOnly;

		_modulesOnlineOnlySupplier = null;
	}

	@JsonIgnore
	public void setModulesOnlineOnly(
		UnsafeSupplier<Boolean, Exception> modulesOnlineOnlyUnsafeSupplier) {

		_modulesOnlineOnlySupplier = () -> {
			try {
				return modulesOnlineOnlyUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The modulesOnlineOnly field.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean modulesOnlineOnly;

	@JsonIgnore
	private Supplier<Boolean> _modulesOnlineOnlySupplier;			
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	@Schema(description = "The moduleCount field.")
	public Long getModuleCount() {
		if (_moduleCountSupplier != null) {
			moduleCount = _moduleCountSupplier.get();

			_moduleCountSupplier = null;
		}

		return moduleCount;
	}

	public void setModuleCount(Long moduleCount) {
		this.moduleCount = moduleCount;

		_moduleCountSupplier = null;
	}

	@JsonIgnore
	public void setModuleCount(
		UnsafeSupplier<Long, Exception> moduleCountUnsafeSupplier) {

		_moduleCountSupplier = () -> {
			try {
				return moduleCountUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The moduleCount field.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long moduleCount;

	@JsonIgnore
	private Supplier<Long> _moduleCountSupplier;
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	


	@Schema(description = "The moduleIds field.")
	public String getModuleIds() {
		if (_moduleIdsSupplier != null) {
			moduleIds = _moduleIdsSupplier.get();

			_moduleIdsSupplier = null;
		}

		return moduleIds;
	}

	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;

		_moduleIdsSupplier = null;
	}

	@JsonIgnore
	public void setModuleIds(
		UnsafeSupplier<String, Exception> moduleIdsUnsafeSupplier) {

		_moduleIdsSupplier = () -> {
			try {
				return moduleIdsUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The moduleIds field.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String moduleIds;

	@JsonIgnore
	private Supplier<String> _moduleIdsSupplier;
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
}