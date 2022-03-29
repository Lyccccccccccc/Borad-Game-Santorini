import Handlebars from "handlebars"
import { Component } from 'react'
import './App.css'

var oldHref = "http://localhost:3000"

interface Cell {
  text: String;
  clazz: String;
  link: String;
}

interface Cells {
  cells: Array<Cell>,
  template: HandlebarsTemplateDelegate<any>,
  instructions: String
}

interface Props {
}

class App extends Component<Props, Cells> {
  constructor(props: Props) {
    super(props);
    this.state = {
      cells: [
        { text: "", clazz: "playable", link: "/play?x=0&y=0" },
        { text: "", clazz: "playable", link: "/play?x=1&y=0" },
        { text: "", clazz: "playable", link: "/play?x=2&y=0" },
        { text: "", clazz: "playable", link: "/play?x=3&y=0" },
        { text: "", clazz: "playable", link: "/play?x=4&y=0" },
        { text: "", clazz: "playable", link: "/play?x=0&y=1" },
        { text: "", clazz: "playable", link: "/play?x=1&y=1" },
        { text: "", clazz: "playable", link: "/play?x=2&y=1" },
        { text: "", clazz: "playable", link: "/play?x=3&y=1" },
        { text: "", clazz: "playable", link: "/play?x=4&y=1" },
        { text: "", clazz: "playable", link: "/play?x=0&y=2" },
        { text: "", clazz: "playable", link: "/play?x=1&y=2" },
        { text: "", clazz: "playable", link: "/play?x=2&y=2" },
        { text: "", clazz: "playable", link: "/play?x=3&y=2" },
        { text: "", clazz: "playable", link: "/play?x=4&y=2" },
        { text: "", clazz: "playable", link: "/play?x=0&y=3" },
        { text: "", clazz: "playable", link: "/play?x=1&y=3" },
        { text: "", clazz: "playable", link: "/play?x=2&y=3" },
        { text: "", clazz: "playable", link: "/play?x=3&y=3" },
        { text: "", clazz: "playable", link: "/play?x=4&y=3" },
        { text: "", clazz: "playable", link: "/play?x=0&y=4" },
        { text: "", clazz: "playable", link: "/play?x=1&y=4" },
        { text: "", clazz: "playable", link: "/play?x=2&y=4" },
        { text: "", clazz: "playable", link: "/play?x=3&y=4" },
        { text: "", clazz: "playable", link: "/play?x=4&y=4" },
      ],
      template: this.loadTemplate(),
      instructions: "It is Player 0's turn"
    };
  }

  loadTemplate (): HandlebarsTemplateDelegate<any> {
    const src = document.getElementById("handlebars");
    return Handlebars.compile(src?.innerHTML, {});
  }

  convertToCell(p: any): Array<Cell> {
    const newCells: Array<Cell> = [];
    for (var i = 0; i < p["cells"].length; i++) {
      var c: Cell = {
        text: p["cells"][i]["text"],
        clazz: p["cells"][i]["clazz"],
        link: p["cells"][i]["link"],
      };
      newCells.push(c);
    }

    return newCells;
  }

  getTurn(p: any): String {
    return p["turn"]
  }

  getWinner(p: any): String | undefined {
    return p["winner"]
  }

  getInstr(turn: String, winner: String | undefined) {
    if (winner === undefined) return "It is Player " + turn +
      "'s turn. /Click two positions to initiate. /MUST select God from below after two players done initiation. " +
      "/Select a worker to move and build. /For Demeter power, click the position of first build again to skip second build."
    else return "Player " + turn + " wins!"
  }

  async newGame() {
    const response = await fetch("newgame");
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    this.setState({ cells: newCells, instructions: "New Game! Take turns to put worker"})
  }

  async demeter() {
    const response = await fetch("demeter");
    const json = await response.json();

    const turn = 1 - Number(this.getTurn(json))
    const newCells: Array<Cell> = this.convertToCell(json);
    this.setState({ cells: newCells, instructions: "Demeter God selected for player " + turn })
  }

  async minotaur() {
    const response = await fetch("minotaur");
    const json = await response.json();

    const turn = 1 - Number(this.getTurn(json))
    const newCells: Array<Cell> = this.convertToCell(json);
    this.setState({ cells: newCells, instructions: "Minotaur God selected for player " + turn })
  }

  async pan() {
    const response = await fetch("pan");
    const json = await response.json();

    const turn = 1 - Number(this.getTurn(json))
    const newCells: Array<Cell> = this.convertToCell(json);
    this.setState({ cells: newCells, instructions: "Pan God selected for player " + turn })
  }

  async nogod() {
    const response = await fetch("nogod");
    const json = await response.json();

    const turn = 1 - Number(this.getTurn(json))
    const newCells: Array<Cell> = this.convertToCell(json);
    this.setState({ cells: newCells, instructions: "No God selected for player " + turn })
  }

  async play(url: String) {
    const href = "play?" + url.split("?")[1];
    const response = await fetch(href);
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const turn = this.getTurn(json)
    const winner = this.getWinner(json)
    const instr = this.getInstr(turn, winner)
    this.setState({ cells: newCells, instructions: instr })
  }

  async switch() {
    if (
      window.location.href === "http://localhost:3000/newgame" &&
      oldHref !== window.location.href
    ) {
      this.newGame();
      oldHref = window.location.href;
    } else if (
      window.location.href.split("?")[0] === "http://localhost:3000/play" &&
      oldHref !== window.location.href
    ) {
      this.play(window.location.href);
      oldHref = window.location.href;
    } else if (
      window.location.href.split("?")[0] === "http://localhost:3000/demeter" &&
      oldHref !== window.location.href
    ) {
      this.demeter();
      oldHref = window.location.href;
    } else if (
      window.location.href.split("?")[0] === "http://localhost:3000/minotaur" &&
      oldHref !== window.location.href
    ) {
      this.minotaur();
      oldHref = window.location.href;
    } else if (
      window.location.href.split("?")[0] === "http://localhost:3000/pan" &&
      oldHref !== window.location.href
    ) {
      this.pan();
      oldHref = window.location.href;
    } else if (
      window.location.href.split("?")[0] === "http://localhost:3000/nogod" &&
      oldHref !== window.location.href
    ) {
      this.nogod();
      oldHref = window.location.href;
    }
  };

  render() {
    this.switch()
    return (
      <div className="App">
        <div
          dangerouslySetInnerHTML={{
            __html: this.state.template({ cells: this.state.cells, instructions: this.state.instructions }),
          }}
        />
      </div>
    )
  };
}

export default App;
