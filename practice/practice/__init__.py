from flask import Flask

from flask_sqlalchemy import SQLAlchemy

from flask_login import LoginManager

app = Flask(__name__)

app.config.from_pyfile('app.conf')

db=SQLAlchemy(app)


app.secret_key="shilianjing"

app.jinja_env.add_extension('jinja2.ext.loopcontrols')

login_manager=LoginManager(app)

login_manager.login_view='/relogin/'





from practice import views,model


