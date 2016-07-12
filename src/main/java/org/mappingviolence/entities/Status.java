package org.mappingviolence.entities;

public enum Status {
  DEFAULT, // Editable only to users who are editors of the POI
  SUBMITTED_FOR_REVIEW, // Editable to users who are editors of the POI,
                        // commentable to Admins
  FLAGGED_FOR_REVISION, // Admin has made a comment, same access privileges as
                        // SUBMITTED_FOR_REVIEW
  APPROVED_FOR_PUBLICATION, PUBLISHED, USER_RECALLED, ADMIN_RECALLED;
}