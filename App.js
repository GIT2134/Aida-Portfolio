import { useState } from "react";
import { useRef } from "react";

import "./style.css";

const myInfo = [
  {
    id: 1,
    text: "Welcome to my professional technology journey! Fueled by an insatiable curiosity and a passion for problem-solving, I entered the world of computers. I was amazed by their potential to transform lives and societies, sparking my passion for computer engineering.",
  },
  {
    id: 2,
    text: "I am a dedicated and hardworking individual, driven by a deep love for web development. My enthusiasm extends to designing and developing websites, as well as the coding and development.",
  },
];

function App() {
  const [elements, setElements] = useState(myInfo);
  const aboutme = useRef(null);
  const education = useRef(null);
  const skills = useRef(null);
  const projects = useRef(null);
  const achievements = useRef(null);
  const development = useRef(null);

  const scrollToSection = (el) => {
    window.scrollTo({ top: el.current.offsetTop, behavior: "smooth" });
  };

  return (
    <>
      <div className="alllinks">
        <ul>
          <li onClick={() => scrollToSection(aboutme)} className="link">
            About Me
          </li>
          <li onClick={() => scrollToSection(education)} className="link">
            Education
          </li>
          <li onClick={() => scrollToSection(skills)} className="link">
            Technical / Computer Skills
          </li>
          <li onClick={() => scrollToSection(projects)} className="link">
            Projects
          </li>
          <li onClick={() => scrollToSection(achievements)} className="link">
            Academic Achievements
          </li>
          <li onClick={() => scrollToSection(development)} className="link">
            Professional Development
          </li>
        </ul>
      </div>
      <Header />
      <main className="mainclass">
        <MyInfo elements={elements} />
      </main>

      <br />

      <br />

      <div ref={aboutme} className="aboutmesection">
        <h3>ABOUT ME</h3>{" "}
        <p className="paragraphs">
          Welcome to my professional technology journey! Driven by an infinite
          curiosity for solving the mysteries of how things work and a passion
          for problem-solving, I explored computers, discovering their
          incredible potential to transform lives and societies. This
          realization sparked my passion for studying computer engineering.
          <br />
          <br /> I recently graduated with a Bachelor of Computer Engineering
          from Concordia University, equipped with a profound love for web
          development, coding, and software development. <br />
          <br />
          My goal is to leverage my knowledge and skills to develop innovative
          solutions and play a pivotal role in advancing technologies in web
          development, software development, and software testing.
        </p>
      </div>
      <div ref={education} className="educationsection">
        <h3>EDUCATION</h3>
        <img src="Concordia.png" height="68" width="68" alt="Concordia Logo" />
        <h4>Concordia University, Montréal, QC</h4>
        <p className="educationparag">
          Bachelor of Computer Engineering <br />
          Jan 2018 - May 2023
        </p>
        <h4>
          <ul>
            <li className="relevantc">Relevant Courses:</li>
          </ul>
        </h4>

        <p className="courses">
          <div className="row">
            <div className="column">
              <ul>
                <li>Android Application Design 📱</li>
                <li>Artificial Intelligence 🤖</li>
                <li>Communication Networks and Protocols 🌐</li>
                <li>Computer Architecture and Design 💻</li>
                <li>Computer Organization and Software 🖥️</li>
                <li>Data Structures and Algorithms 🧠</li>
                <li>Databases 📊</li>
                <li>Hovercraft Design 🚁</li>
                <li>Impact of Technology on Society 🌍💡</li>
              </ul>
            </div>
            <div className="column">
              <ul>
                <li>Information Systems Security 🔐</li>
                <li>Introduction to Real‑Time Systems ⌛</li>
                <li>Operating Systems 🖥️</li>
                <li>Printer Switch Capstone Design 🖨️</li>
                <li>Programming Methodology I & II 💻📚</li>
                <li>Software Process 🔄</li>
                <li>Software Requirements and Specifications 📄🔍</li>
                <li>Technical Writing and Communication 📝</li>
                <li>Engineering Management Principles and Economics 💼</li>
              </ul>
            </div>
          </div>
        </p>
      </div>
      <div ref={skills} className="skillssection">
        <h3>TECHNICAL / COMPUTER SKILLS</h3>{" "}
        <p className="compskills">
          🌐Web Development: HTML, CSS, JavaScript, React, Node.js
          <br />
          ⚙️Programming Languages: SQL, Java, C, Python, C++, MATLAB
          <br /> 🛢️Databases: MySQL, PostgreSQL, Oracle, Firebase <br />
          🔄Project Management Methodologies: Agile, Scrum <br />
          🛠️Tools: <br />
          <ul className="listtools">
            <li>Atlassian Jira, Slack</li>
            <li>
              Visual Studio, Eclipse, Android Studio, PyCharm Professional,
              Jupyter Notebook, Spyder{" "}
            </li>

            <li>
              Microsoft Office (Excel, Word, Teams, SharePoint, PowerPoint)
            </li>
            <li>
              MySQL Server, MySQL Workbench, pgAdmin, Toad, DBeaver, Oracle VM
              18c VirtualBox{" "}
            </li>
            <li>Figma, Canva, Miro, Balsamiq Wireframes</li>
            <li>CoppeliaSim, AVR Studio, Simulink, Arduino IDE, KiCad</li>
            <li>VMware Workstation, QNX Software Center, QNX Momentics IDE</li>
          </ul>
          📁Version Control System: GitHub
          <br />
          💻Operating Systems: Linux, Windows, Google’s Android OS, Apple iOS,
          Mainframe
        </p>
      </div>
      <div ref={projects} className="projectssection">
        <h3>PROJECTS</h3>

        <div className="happ">
          <img
            src="Android.png"
            alt="Android Application Design"
            className="imgandroid"
          />

          <div className="boxtext">
            <h3>
              <a href="" className="androidtitle">
                Android Application Design
              </a>
            </h3>
            <p>
              {" "}
              <ul className="listofdesc">
                <li>
                  Designed and tested a Java-based Android app for water quality
                  monitoring
                </li>
                <li>
                  Utilized Firebase as a Realtime cloud-hosted NoSQL Database
                  for efficient data storage
                </li>
              </ul>
            </p>
            <button className="buttonapp">Java</button>
            <button className="buttonapp"> C </button>
            <button className="buttonapp">Agile</button>
            <button className="buttonapp">Scrum</button>
            <button className="buttonapp"> Git</button>
            <button className="buttonapp">Balsamiq Wireframes</button>
            <button className="buttonapp"> Software Development</button>
            <button className="buttonapp">Firebase Database</button>
          </div>
        </div>

        <div className="happ">
          <img
            src="capstone.png"
            alt="Printer Switch Capstone Design"
            className="imgandroid"
          />

          <div className="boxtext">
            <h3>
              <a href="" className="androidtitle">
                Printer Switch Capstone Design
              </a>
            </h3>
            <p>
              {" "}
              <ul className="listofdesc">
                <li>
                  Designed and implemented a C-programmed switch for interfacing
                  RS-232 inputs with a USB printer
                </li>
                <li>
                  Accomplished the selection, planning, design, prototyping, and
                  delivery of the final product{" "}
                </li>
                <li>
                  Utilized Jira for deadline-driven time management, effectively
                  integrating project timeline{" "}
                </li>
              </ul>
            </p>

            <button className="buttonapp"> C </button>
            <button className="buttonapp">Atlassian Jira</button>
            <button className="buttonapp">Agile</button>
            <button className="buttonapp">Scrum</button>
            <button className="buttonapp">Git</button>
            <button className="buttonapp"> Software Development</button>
          </div>
        </div>

        <div className="happ">
          <img src="Databases.png" alt="Databases" className="imgandroid" />

          <div className="boxtext">
            <h3>
              <a href="" className="androidtitle">
                Databases
              </a>
            </h3>
            <p>
              {" "}
              <ul className="listofdesc">
                <li>
                  {" "}
                  Created and implemented relational database designs, using SQL
                  and MySQL
                </li>
                <li>Applied normalization and de-normalization techniques</li>
                <li>
                  Implemented defining constraints, and wrote DDL and DML SQL
                  queries and scripts
                </li>
              </ul>
            </p>
            <button className="buttonapp">SQL</button>
            <button className="buttonapp"> MySQL</button>
            <button className="buttonapp">DBeaver</button>
            <button className="buttonapp">Toad</button>
            <button className="buttonapp"> MySQL Workbench</button>
          </div>
        </div>

        <div className="happ">
          <img
            src="AI.png"
            alt="Artificial Intelligence "
            className="imgandroid"
          />

          <div className="boxtext">
            <h3>
              <a href="" className="androidtitle">
                Artificial Intelligence{" "}
              </a>
            </h3>
            <p>
              {" "}
              <ul className="listofdesc">
                <li>
                  Explored text classification with Machine Learning (ML)
                  algorithms, using a large dataset and Python
                </li>

                <li>
                  Utilized libraries such as NumPy, Pandas, and scikit-learn
                </li>

                <li>
                  Executed an 80% training and 20% testing data split strategy
                  for a machine learning model
                </li>
              </ul>
            </p>
            <button className="buttonapp">Python</button>
            <button className="buttonapp">PyCharm Professional</button>
            <button className="buttonapp">Jupyter Notebook</button>
            <button className="buttonapp">Git</button>
          </div>
        </div>

        <div className="happ">
          <img
            src="learn.png"
            alt="Software Requirements and Specification "
            className="imgandroid"
          />

          <div className="boxtext">
            <h3>
              <a href="" className="androidtitle">
                Software Requirements and Specification{" "}
              </a>
            </h3>
            <p>
              {" "}
              <ul className="listofdesc">
                <li>
                  Produced a detailed report including risk analysis,
                  requirement validation, and quality assurance (QA)
                </li>

                <li>
                  Utilized Figma to integrate a graphical user interface (GUI)
                </li>
              </ul>
            </p>
            <button className="buttonapp">Documentation</button>
            <button className="buttonapp">Presentation</button>
            <button className="buttonapp">Git</button>
            <button className="buttonapp">Figma</button>
            <button className="buttonapp">graphical user interface(GUI)</button>
          </div>
        </div>

        <div className="happ">
          <img
            src="network.png"
            alt="
          Communication Networks and Protocols "
            className="imgandroid"
          />

          <div className="boxtext">
            <h3>
              <a href="" className="androidtitle">
                Communication Networks and Protocols{" "}
              </a>
            </h3>
            <p>
              {" "}
              <ul className="listofdesc">
                <li>
                  Implemented a file transfer system in a Linux environment
                  using Python
                </li>

                <li>
                  Designed the system to include one server capable of
                  efficiently handling multiple clients
                </li>
              </ul>
            </p>
            <button className="buttonapp">Python</button>
            <button className="buttonapp">Linux</button>
            <button className="buttonapp">Git</button>
            <button className="buttonapp">
              networking protocols (TCP/UDP)
            </button>
            <button className="buttonapp">Client-Server Model</button>
            <button className="buttonapp">PyCharm</button>
            <button className="buttonapp">Spyder</button>
          </div>
        </div>

        <div className="happ">
          <img
            src="hover.png"
            alt="
          
          Hovercraft Design "
            className="imgandroid"
          />

          <div className="boxtext">
            <h3>
              <a href="" className="androidtitle">
                Hovercraft Design{" "}
              </a>
            </h3>
            <p>
              {" "}
              <ul className="listofdesc">
                <li>
                  Designed a hovercraft using C and Lua programming languages
                  and navigated it in a predefined track with three obstacles
                  within a 60-second timeframe
                </li>
              </ul>
            </p>
            <button className="buttonapp">C (Programming Language)</button>
            <button className="buttonapp">Software Development</button>
            <button className="buttonapp">Coppeliasim</button>
          </div>
        </div>

        <div className="happ">
          <img
            src="websitedesign.png"
            alt="
          
          Website Design "
            className="imgandroid"
          />

          <div className="boxtext">
            <h3>
              <a href="" className="androidtitle">
                Web Design{" "}
              </a>
            </h3>
            <p>
              {" "}
              <ul className="listofdesc">
                <li>
                  Designed and implemented a website using web development
                  technologies
                </li>
              </ul>
            </p>
            <button className="buttonapp">React</button>
            <button className="buttonapp">Node.js</button>
            <button className="buttonapp">HTML</button>
            <button className="buttonapp">CSS</button>
            <button className="buttonapp">JavaScript</button>
            <button className="buttonapp">Visual Studio</button>
          </div>
        </div>

        <div className="happ">
          <img
            src="designingweb.png"
            alt="
          
          Website Design "
            className="imgandroid"
          />

          <div className="boxtext">
            <h3>
              <a href="" className="androidtitle">
                Job search Website Design{" "}
              </a>
            </h3>
            <p>
              {" "}
              <ul className="listofdesc">
                <li>
                  Designed and implemented a prototype job search website
                  allowing user to enter their job title, location, job category
                  and search
                </li>
              </ul>
            </p>
            <button className="buttonapp">React</button>
            <button className="buttonapp">Node.js</button>
            <button className="buttonapp">HTML</button>
            <button className="buttonapp">CSS</button>
            <button className="buttonapp">JavaScript</button>
            <button className="buttonapp">Visual Studio</button>
          </div>
        </div>
      </div>
      <div ref={achievements} className="achievementssection">
        <h3>ACADEMIC ACHIEVEMENTS</h3>
        <img src="Concordia.png" height="68" width="68" alt="Concordia Logo" />
        <h4>Honorable Mention Award</h4>
        <p className="educationparag">
          Concordia University, Montréal, QC <br />
          Jan 2018 - May 2023 <br />
          <ul className="courses">
            <li>
              Recognized for Android application design and project requirements
              fulfillment
            </li>
          </ul>
        </p>
      </div>
      <div ref={development} className="developmentsection">
        <h3>PROFESSIONAL DEVELOPMENT</h3>
        <img src="udemylogo.png" height="68" width="68" alt="Udemy Logo" />
        <h4>Udemy Web Development Bootcamp </h4>
        <p className="educationparag">
          Sep 2023 - Jan 2024
          <ul className="courses">
            <li>
              Acquired knowledge of HTML, CSS properties, JavaScript, and React
              and Node.js
            </li>
          </ul>
        </p>
        <img src="udemylogo.png" height="68" width="68" alt="Udemy Logo" />
        <h4>Udemy Python Bootcamp </h4>
        <p className="educationparag">
          Sep 2023 - Present
          <ul className="courses">
            <li>
              Expanded knowledge of Python programming skills and applied it to
              create several projects, including a variety of games
            </li>
          </ul>
        </p>
        <img src="udemylogo.png" height="68" width="68" alt="Udemy Logo" />
        <h4>Udemy PostgreSQL Database Administration </h4>
        <p className="educationparag">
          July 2023 - October 2023
          <ul className="courses">
            <li>
              Learned how to install, write basic queries, retrieve data from
              tables, understand table joining to retrieve data, create tables,
              handle data types, and work with expressions, operators, and
              strings
            </li>
          </ul>
        </p>
      </div>
    </>
  );
}

function Header() {
  const apptitle = "Hi, I'm Aida Kordi";
  return (
    <div>
      <h1>{apptitle}</h1>
      <h2>
        A recent computer engineering graduate with a passion
        <br /> for web development and software development
      </h2>
      <br />
    </div>
  );
}

function MyInfo({ elements }) {
  return (
    <section>
      <ul className="my-info">
        {elements.map((element) => (
          <InfoElement element={element} />
        ))}
      </ul>
    </section>
  );
}
function InfoElement({ element }) {
  return (
    <li className="element">
      <p>{element.text}</p>
      <br />
    </li>
  );
}

export default App;
