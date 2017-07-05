/*
 * Copyright (C) 2017 VSCT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {BotMessage, DialogReport} from "app/shared/model/dialog-data";
import {ApplicationScopedQuery} from "tock-nlp-admin/src/app/model/commons";

export class BotDialogRequest extends ApplicationScopedQuery {

  constructor(public botApplicationConfigurationId: string,
              public message: BotMessage,
              public namespace: string,
              public applicationName: string,
              public language: string) {
    super(namespace, applicationName, language)
  }

}

export class BotDialogResponse {

  constructor(public messages: BotMessage[]) {
  }

  static fromJSON(json?: any): BotDialogResponse {
    const value = Object.create(BotDialogResponse.prototype);

    const result = Object.assign(value, json, {
      messages: BotMessage.fromJSONArray(json.messages),
    });

    return result;
  }

}

export class TestMessage {
  constructor(public bot: boolean,
              public message?: BotMessage) {

  }
}

export class TestPlan {

  public testPlanExecutions: TestPlanExecution[];
  public botName: string;
  public displayDialog: boolean;
  public displayExecutions: boolean;

  constructor(public dialogs: DialogReport[],
              public name: string,
              public applicationId: string,
              public namespace: string,
              public nlpModel: string,
              public locale: string,
              public botApplicationConfigurationId: string,
              public _id?: string) {
  }

  fillDialogExecutionReport(report: DialogExecutionReport) {
    if (report.error) {
      const d = this.dialogs.find(d => report.dialogReportId === d.id);
      if (d) {
        const aIndex = d.actions.findIndex(a => a.id === report.errorActionId);
        if (aIndex === -1) {
          report.dialogReport = new DialogReport(d.actions, d.id);
        } else {
          report.dialogReport = new DialogReport(d.actions.slice(0, Math.min(aIndex + 1, d.actions.length)), d.id)
        }
        report.dialogReport.displayActions = true;
      }
    }
  }

  static fromJSON(json?: any): TestPlan {
    const value = Object.create(TestPlan.prototype);

    const result = Object.assign(value, json, {
      dialogs: DialogReport.fromJSONArray(json.dialogs),
    });

    return result;
  }

  static fromJSONArray(json?: Array<any>): TestPlan[] {
    return json ? json.map(TestPlan.fromJSON) : [];
  }

}

export class TestPlanExecution {

  displayExecution: boolean;

  constructor(public testPlanId: string,
              public dialogs: DialogExecutionReport[],
              public nbErrors: number,
              public date: Date,
              public duration: Date,
              public _id?: string) {
  }

  static fromJSON(json?: any): TestPlanExecution {
    const value = Object.create(TestPlanExecution.prototype);

    const result = Object.assign(value, json, {
      dialogs: DialogExecutionReport.fromJSONArray(json.dialogs),
    });

    return result;
  }

  static fromJSONArray(json?: Array<any>): TestPlanExecution[] {
    return json ? json.map(TestPlanExecution.fromJSON) : [];
  }

}

export class DialogExecutionReport {

  dialogReport: DialogReport;

  constructor(public dialogReportId: string,
              public error: boolean,
              public errorActionId?: string,
              public returnedMessage?: BotMessage,
              public errorMessage?: string) {
  }

  static fromJSON(json?: any): DialogExecutionReport {
    const value = Object.create(DialogExecutionReport.prototype);

    const result = Object.assign(value, json, {
      returnedMessage: BotMessage.fromJSON(json.returnedMessage),
    });

    return result;
  }

  static fromJSONArray(json?: Array<any>): DialogExecutionReport[] {
    return json ? json.map(DialogExecutionReport.fromJSON) : [];
  }

}