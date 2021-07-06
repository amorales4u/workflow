import { Component, AfterViewInit } from '@angular/core';
import * as c3 from 'c3';

@Component({
  selector: 'app-tasks-chart',
  templateUrl: './tasks-chart.component.html',
  styleUrls: ['./tasks-chart.component.css']
})

export class TasksChartComponent implements AfterViewInit {

  constructor() {}

  generateGlobalData() {

  }
  generateTipoSolicitud() {
    return c3.generate({
      bindto: '.solicitudes',
      data: {
        columns: [
          ['En Resolución', 5, 6, 3, 7, 9, 30],
          ['Vencidas', 1, 2, 8, 3, 4, 17],
          ['Aceptadas', 1, 2, 8, 3, 4, 17],
          ['Rechazadas', 1, 2, 8, 3, 4, 17],
          ['Atendidas', 1, 2, 8, 3, 4, 17],
          ['Total', 10, 2, 8, 3, 4, 27],
        ],
        type: 'bar'
      },
      axis: {
        y: {
          show: true,
          tick: {
            count: 0,
            outer: false
          }
        },
        x: {
          type: 'category',
          categories: ['AD', 'AO', 'RC', 'RD', 'RI', 'Total']
        }
      },
      bar: {
        width: 8
      },
      padding: {
        top: 40,
        right: 10,
        bottom: 0,
        left: 20
      },
      point: {
        r: 0
      },
      legend: {
        hide: false
      },
      color: {
        pattern: ['#2CBFDB', '#fc5c46', '#24b332', '#db0b34', '#296E0C', '#072c96' ]
      }
    });
  }

  generateSegmento() {
    return c3.generate({
      bindto: '.segmentos',
      data: {
        columns: [
          ['En Resolución', 5, 6, 3, 7, 9, 5, 6, 3, 7, 9, 5, 6, 3, 7, 9, 12],
          ['Vencidas', 1, 2, 8, 3, 4, 5, 6, 3, 7, 9, 5, 6, 3, 7, 9, 12],
          ['Aceptadas', 1, 2, 8, 3, 4, 5, 6, 3, 7, 9, 5, 6, 3, 7, 9, 12],
          ['Rechazadas', 1, 2, 8, 3, 4, 5, 6, 3, 7, 9, 5, 6, 3, 7, 9, 12],
          ['Atendidas', 1, 2, 8, 3, 4, 5, 6, 3, 7, 9, 5, 6, 3, 7, 9, 12],
          ['Total', 10, 2, 8, 3, 4, 5, 6, 3, 7, 9, 5, 6, 3, 7, 9, 30],
        ],
        type: 'bar'
      },
      axis: {
        y: {
          show: true,
          tick: {
            count: 0,
            outer: false
          }
        },
        x: {
          type: 'category',
          categories: ['AI', 'BEI', 'BP', 'BPV', 'CAP', 'CBP', 'DF', 'DOM', 'FID', 'LIQ', 'PAT', 'PVA', 'RFAT', 'SUC', 'TES', 'Total']
        }
      },
      bar: {
        width: 8
      },
      padding: {
        top: 40,
        right: 10,
        bottom: 0,
        left: 20
      },
      point: {
        r: 0
      },
      legend: {
        hide: false
      },
      color: {
        pattern: ['#2CBFDB', '#fc5c46', '#24b332', '#db0b34', '#296E0C', '#072c96' ]
      }
    });
  }
  generateEntidad() {
    return c3.generate({
      bindto: '.entidades',
      data: {
        columns: [
          ['En Resolución', 5, 6,  30],
          ['Vencidas', 1, 2,  17],
          ['Aceptadas', 1, 2,   17],
          ['Rechazadas', 1, 2,  17],
          ['Atendidas', 1, 2,   17],
          ['Total', 10, 2,  27],
        ],
        type: 'bar'
      },
      axis: {
        y: {
          show: true,
          tick: {
            count: 0,
            outer: false
          }
        },
        x: {
          type: 'category',
          categories: ['Area 01', 'Area 02', 'Total']
        }
      },
      bar: {
        width: 8
      },
      padding: {
        top: 40,
        right: 10,
        bottom: 0,
        left: 20
      },
      point: {
        r: 0
      },
      legend: {
        hide: false
      },
      color: {
        pattern: ['#2CBFDB', '#fc5c46', '#24b332', '#db0b34', '#296E0C', '#072c96' ]
      }
    });
  }


  generateDivision() {
    return c3.generate({
      bindto: '.divisiones',
      data: {
        columns: [
          ['En Resolución', 5, 6, 3, 7, 9, 10, 30],
          ['Vencidas', 1, 2, 8, 3, 4, 9, 17],
          ['Aceptadas', 1, 2, 8, 3, 4, 8, 17],
          ['Rechazadas', 1, 2, 8, 3, 4, 7, 17],
          ['Atendidas', 1, 2, 8, 3, 4, 6, 17],
          ['Total', 10, 2, 8, 3, 4, 5, 27],
        ],
        type: 'bar'
      },
      axis: {
        y: {
          show: true,
          tick: {
            count: 0,
            outer: false
          }
        },
        x: {
          type: 'category',
          categories: ['Area Central', 'Centro', 'Centro MME', 'Centro SME', 'Occidente', 'Sur', 'Total']
        }
      },
      bar: {
        width: 8
      },
      padding: {
        top: 40,
        right: 10,
        bottom: 0,
        left: 20
      },
      point: {
        r: 0
      },
      legend: {
        hide: false
      },
      color: {
        pattern: ['#2CBFDB', '#fc5c46', '#24b332', '#db0b34', '#296E0C', '#2961ff' ]
      }
    });
  }


  ngAfterViewInit() {
    const chart1 = this.generateTipoSolicitud();
    const chart2 = this.generateSegmento();
    const chart3 = this.generateEntidad();
    const chart4 = this.generateDivision();

  }
}
