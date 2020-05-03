/*
 * Copyright (C) 2017/2020 e-voyageurs technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import {map} from 'rxjs/operators';
import {Component, Input, OnInit} from "@angular/core";
import {StateService} from "../core-nlp/state.service";
import {NlpService} from "../nlp-tabs/nlp.service";
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import {ApplicationService} from "../core-nlp/applications.service";
import {EntityDefinition, EntityType} from "../model/nlp";
import {ConfirmDialogComponent} from "../shared-nlp/confirm-dialog/confirm-dialog.component";
import { NbToastrService } from '@nebular/theme';

@Component({
  selector: 'tock-entity-details',
  templateUrl: './entity-details.component.html',
  styleUrls: ['./entity-details.component.css']
})
export class EntityDetailsComponent implements OnInit {

  @Input()
  entity: EntityDefinition;
  @Input()
  paddingLeft: number = 0;
  @Input()
  entityType: EntityType;

  constructor(public state: StateService,
              private nlp: NlpService,
              private toastrService: NbToastrService,
              private dialog: MatDialog,
              private applicationService: ApplicationService) {
  }

  ngOnInit() {
  }

  findEntityType(): EntityType {
    return this.state.findEntityTypeByName(this.entity.entityTypeName);
  }

  update() {
    this.nlp.updateEntityDefinition(
      this.state.createUpdateEntityDefinitionQuery(this.entity)
    ).pipe(map(_ => this.applicationService.reloadCurrentApplication()))
      .subscribe(_ => this.toastrService.show(`Entity updated`, "Update", {duration: 2000}));
  }

  remove() {
    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: `Remove the subentity ${this.entity.entityTypeName}`,
        subtitle: "Are you sure?",
        action: "Remove"
      }
    } as MatDialogConfig);
    dialogRef.afterClosed().subscribe(result => {
      if (result === "remove") {
        this.nlp.removeSubEntity(this.state.currentApplication, this.entityType, this.entity).subscribe(
          _ => {
            this.state.resetConfiguration();
            this.toastrService.show(`Subentity ${this.entity.entityTypeName} removed`, "Remove Subentity", {duration: 2000});
          },
          _ => this.toastrService.show(`Remove Subentity ${this.entity.entityTypeName} failed`, "Error", {duration: 5000})
        );
      }
    });
  }

  subEntities(): EntityDefinition[] {
    const entityType = this.findEntityType();
    if (!entityType) {
      return [];
    } else {
      //filter sub entities already seen (avoid direct recursive problem)
      return entityType.subEntities.filter(s => s.entityTypeName !== entityType.name);
    }
  }

}
