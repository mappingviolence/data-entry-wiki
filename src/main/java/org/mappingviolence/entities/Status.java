package org.mappingviolence.entities;

public enum Status {
  DRAFT, // Editable only to users who are editors of the POI
  IN_POOL,
  PUBLISHED;
}