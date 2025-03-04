# Introduction #
- This is a Proof of Concept (POC) to index additional fields into Elasticsearch documents from a Many to Many Objects relationship and expose those fields to the Headless API search.
- The POC uses Company scoped entities Student and Module with a Many to Many relationship defined from Student to Module.
- A set of custom fields related to the Students mapped Modules are added to the root of Student Elasticsearch Document.
- If a Student to Module mapping is added the Student record is reindexed to reflect the change in the Student Elasticsearch document.
- If a Student to Module mapping is removed the Student record is reindexed to reflect the change in the Student Elasticsearch document.
- If a Module is updated, the mapped Student records are each reindexed to reflect the changes in the mapped Student Elasticsearch documents.
- If a Module is deleted, the mapped Student records are each reindexed to reflect the change in the mapped Student Elasticsearch documents.

Additional Student Elasticsearch Fields:

```
{
"moduleCount": 3,
"moduleCredits": 19,
"moduleDepartments": "Commerce",
"moduleIds": "34775,34778,34780",
"moduleLecturers": "Roger White, Arthur Carr, Peter Gregory",
"moduleNames": "Basis Accounting 101, Intermediate Accounting 102, Advanced Accounting 103",
"modulesOnlineOnly": false
}
```

where:

- moduleCount is the total number of Modules mapped to this Student.
- moduleCredits is the combined total of credits across all the Modules mapped to this Student.
- moduleDepartments is a comma separated, de-duplicated list of departments for all the Modules mapped to this Student.
- moduleIds is a comma separated list of the ObjectEntryIds for all the Modules mapped to this Student.
- moduleLecturers is a comma separated, de-duplicated list of module lecturers for all the Modules mapped to this Student.
- moduleNames is a comma separated list of module names for all the Modules mapped to this Student.
- modulesOnlineOnly is true if all of a Students Modules are online, otherwise it is false.

## Environment ##
- Liferay DXP version: 2025.Q1.0
- JDK: 21
- Liferay Dev Studio: Version: 3.9.8.202212271250-ga9
- The POC uses the default language i.e. en-US.

## Setup Steps ##
1. Setup a Liferay DXP
2. Go to Objects and import Objects Folder using 'Object_Folder_ManyToManyPOC_34502_20250304154433737.json'
3. Blacklist Component:
com.liferay.portal.search.rest.internal.resource.v1_0.SearchResultResourceImpl
4. Update the constants in MWConstants.java based on the details in the environment, build and deploy the OSGi modules
- STUDENT_OBJECT_ENTRY_CLASS_NAME: Go to Control Panel > Configuration Search > Index Actions, search for 'Student' and copy it's Object Definition value without the brackets e.g. com.liferay.object.model.ObjectDefinition#G9X2
- MODULE_OBJECT_DEFINITION_ID: The 'ID' value from the Module Object Definition.
- MANY_TO_MANY_RELATIONSHIP_ID: Go to Control Panel > Objects > Student > Relationships. Open Chrome Dev Tools > Network and open the student_modules relationship. Find the /manage request in the Network traffic and copy the objectRelationshipId request parameter value e.g. 34614.
- MANY_TO_MANY_REVERSE_RELATIONSHIP_ID: Go to Control Panel > Objects > Module > Relationships. Open Chrome Dev Tools > Network and open the student_modules relationship. Find the /manage request in the Network traffic and copy the objectRelationshipId request parameter value e.g. 34615.
5. Create or Import Students and Module records

## OSGi Components & Modules ##
1. portal-search-rest-impl-fragment / portal.search.rest.impl.fragment-1.0.0.jar: OSGi fragment module to export 'internal' packages used by MWSearchResultResourceImpl.
2. portal-search-rest-impl-override / portal.search.rest.impl.override-1.0.0.jar: Contains all the custom code:
- MWSearchResultResourceImpl.java: Override of OOTB class SearchResultResourceImpl to add the custom fields to the Student Headless API 'Search Result' entity.
- MWSearchResult.java: Override of the OOTB class SearchResult to add the custom fields to the Student Headless API 'Search Result' entity.
- MWConstants.java IDs to refer to entities, relationships.
- MWFieldConstants.java Field names.
- ObjectEntryModelListener.java: Used to identify changes to the Module attributes so the mapped Student records can be reindex in Elasticsearch.
- ObjectRelationshipLocalServiceServiceWrapper.java: Used to identify mapping addition, mapping deletion and Module deletion so the mapped Student records can be reindex in Elasticsearch.
- StudentObjectEntryDocumentContributor.java: Adds the additional fields to the Student Elasticsearch document. 
- StudentObjectEntryKeywordQueryContributor.java: Makes the additional fields searchable fields for the Student ObjectDefinition.

## Headless Search ##
To Perform a search using the Headless Search API:
1. Enable Instance Settings > Feature Flags > Release > Search Headless API (LPS-179669)
2. Go to Applications > Blueprints
3. Add a Custom Element using the sample moduleDepartments_customElement.txt
4. Add a Blueprint using the Custom Element from above and with the matching sample moduleDepartments_parameterConfiguration.txt for the Configuration > Parameter Configuration.
5. Save the Blueprint and note the ERC.
6. Go to Headless Search API e.g. http://localhost:8080/o/api?endpoint=http://localhost:8080/o/search/v1.0/openapi.json
7. Expand Search Result > postSearchResult and paste in the following into the Request Body field, replacing the ERC with the one from above:

```
{
  "attributes": {
    "search.experiences.blueprint.external.reference.code": "423ff6e9-7e2e-1322-93a3-4f7c1707be38",
	"search.experiences.moduleDepartment":"commerce"
  }
}
```

8. Enter * in the Search field and Click Execute.
9. All records which contain Commerce in the moduleDepartments field will be returned, including the additional fields.
10. Sample response:

```
{
  "items": [
    {
      "dateCreated": "2025-03-04T14:26:07Z",
      "dateModified": "2025-03-04T14:26:07Z",
      "description": "studentName: Filipe, studentNumber: LR-1003",
      "moduleCount": 4,
      "moduleCredits": 26,
      "moduleDepartments": "Commerce, Medicine, Arts",
      "moduleIds": "34778,34782,34792,34794",
      "moduleLecturers": "Arthur Carr, Elaine White, Arthur Fent, Harry Hole",
      "moduleNames": "Intermediate Accounting 102, Intro to Medicine 101, Irish Music 101, History & Politics 101",
      "modulesOnlineOnly": false,
      "score": 4.8652306,
      "title": "Filipe"
    },
    {
      "dateCreated": "2025-03-04T14:27:39Z",
      "dateModified": "2025-03-04T14:27:39Z",
      "description": "studentName: Niamh, studentNumber: LR-1009",
      "moduleCount": 5,
      "moduleCredits": 29,
      "moduleDepartments": "Commerce, Medicine, Arts",
      "moduleIds": "34775,34784,34786,34792,34794",
      "moduleLecturers": "Roger Babbage, Sally Greene, Max Kerr, Arthur Fent, Harry Hole",
      "moduleNames": "Basis Accounting 101, Biology 101, Fundamentals of Philosophy 101, Irish Music 101, History & Politics 101",
      "modulesOnlineOnly": false,
      "score": 4.8652306,
      "title": "Niamh"
    },
    {
      "dateCreated": "2025-03-04T14:25:36Z",
      "dateModified": "2025-03-04T14:25:36Z",
      "description": "studentName: Michael, studentNumber: LR-1001",
      "moduleCount": 3,
      "moduleCredits": 23,
      "moduleDepartments": "Commerce",
      "moduleIds": "34775,34778,34780",
      "moduleLecturers": "Roger Babbage, Arthur Carr, Peter Gregory",
      "moduleNames": "Basis Accounting 101, Intermediate Accounting 102, Advanced Accounting 103",
      "modulesOnlineOnly": false,
      "score": 4.8652306,
      "title": "Michael"
    },
    {
      "dateCreated": "2025-03-04T14:26:46Z",
      "dateModified": "2025-03-04T14:26:46Z",
      "description": "studentName: Evelyn, studentNumber: LR-1006",
      "moduleCount": 3,
      "moduleCredits": 23,
      "moduleDepartments": "Commerce",
      "moduleIds": "34775,34778,34780",
      "moduleLecturers": "Roger Babbage, Arthur Carr, Peter Gregory",
      "moduleNames": "Basis Accounting 101, Intermediate Accounting 102, Advanced Accounting 103",
      "modulesOnlineOnly": false,
      "score": 4.8652306,
      "title": "Evelyn"
    },
    {
      "dateCreated": "2025-03-04T14:27:13Z",
      "dateModified": "2025-03-04T14:27:13Z",
      "description": "studentName: Daniel, studentNumber: LR-1008",
      "moduleCount": 5,
      "moduleCredits": 42,
      "moduleDepartments": "Commerce, Medicine, Arts",
      "moduleIds": "34775,34782,34788,34790,34794",
      "moduleLecturers": "Roger Babbage, Elaine White, Joanne Jett, Joe Smith, Harry Hole",
      "moduleNames": "Basis Accounting 101, Intro to Medicine 101, European Studies 101, Creative Writing 101, History & Politics 101",
      "modulesOnlineOnly": false,
      "score": 4.8652306,
      "title": "Daniel"
    },
    {
      "dateCreated": "2025-03-04T14:26:28Z",
      "dateModified": "2025-03-04T14:26:28Z",
      "description": "studentName: Leanne, studentNumber: LR-1005",
      "moduleCount": 4,
      "moduleCredits": 30,
      "moduleDepartments": "Commerce, Medicine, Arts",
      "moduleIds": "34775,34782,34786,34790",
      "moduleLecturers": "Roger Babbage, Elaine White, Max Kerr, Joe Smith",
      "moduleNames": "Basis Accounting 101, Intro to Medicine 101, Fundamentals of Philosophy 101, Creative Writing 101",
      "modulesOnlineOnly": false,
      "score": 4.8652306,
      "title": "Leanne"
    },
    {
      "dateCreated": "2025-03-04T14:26:54Z",
      "dateModified": "2025-03-04T14:26:54Z",
      "description": "studentName: Tarun, studentNumber: LR-1007",
      "moduleCount": 3,
      "moduleCredits": 23,
      "moduleDepartments": "Commerce",
      "moduleIds": "34775,34778,34780",
      "moduleLecturers": "Roger Babbage, Arthur Carr, Peter Gregory",
      "moduleNames": "Basis Accounting 101, Intermediate Accounting 102, Advanced Accounting 103",
      "modulesOnlineOnly": false,
      "score": 4.8652306,
      "title": "Tarun"
    }
  ],
  "lastPage": 1,
  "page": 1,
  "pageSize": 20,
  "totalCount": 7
}
```
