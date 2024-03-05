package com.rhett.thymebackend.datasource.gcp

interface GCPProjectProvider {
   fun getProjectId(): String;
}