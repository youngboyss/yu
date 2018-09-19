#-*- encoding=UTF-8 -*-
import os
import uuid

from practice import app,db

from flask import render_template,request,redirect,flash,get_flashed_messages

from practice.model import User,Image

import random,hashlib

from flask_login import login_user,logout_user,current_user,login_required





@app.route("/")
def index():
    image=Image.query.order_by(db.desc(Image.id)).limit(10).all()
    return render_template("index.html",images=image)




@app.route("/image/<int:image_id>/")
def image(image_id):
    image=Image.query.get(image_id)
    if image==None:
        return redirect('/')
    return render_template('pageDetail.html',image=image)

@app.route("/profile/<int:user_id>/")
@login_required
def profile(user_id):
    user=User.query.get(user_id)
    if user==None:
        return redirect("/")
    return render_template('profile.html',user=user)


@app.route("/relogin/")
def relogin():
    msg=''
    for s in get_flashed_messages(with_categories=False,category_filter=['relogin']):
         msg=msg+s
    return render_template('login.html',msg=msg)


def redirect_msg(target,msg,category):

    if msg!=None:
        flash(msg,category=category)
        return redirect(target)


@app.route("/login/",methods={'GET','POST'})
def login():
    username=request.values.get('username').strip()
    password=request.values.get('password').strip()

    if username==''or password=='':
        return redirect_msg('/relogin/', u'用户名或密码为空', 'relogin')

    user=User.query.filter_by(username=username).first()
    if user==None:
       return redirect_msg('/relogin/',u'用户不存在','relogin')

    m=hashlib.md5()
    m.update(password+user.salt)
    if(m.hexdigest()!=user.password):
        return redirect_msg('/relogin/',u'密码错误','relogin')

    login_user(user)

    return redirect('/')


@app.route("/reg/",methods=['GET','POST'])
def re():
   username=request.values.get('username').strip()
   password=request.values.get('password').strip()

   if username==''or password=='':
       return redirect_msg('/relogin/',u'用户名或密码为空','relogin')

   user=User.query.filter_by(username=username).first()

   if user !=None:
       return redirect_msg('/relogin/',u'用户已经存在','relogin')

   salt='.'.join(random.sample('0123456789ABCDEFGHIJKeqeweqsxzx',10))
   m=hashlib.md5()
   m.update(password+salt)
   password = m.hexdigest()
   user=User(username,password,salt)

   db.session.add(user)
   db.session.commit()

   login_user(user)
   return redirect('/')



@app.route('/logout/')
def logout():
    logout_user()
    return redirect('/')

# @app.route('/po/')
# def ui():
#     args = request.args.get("name") or "args没有参数"
#
#     return 'Hello'+args
# #
#

@app.errorhandler(404)
def page_not_found(error):
    return render_template('not_found.html',url=request.url),404

def save_to_local(file,file_name):
    save_dir=app.config['UPLOAD_DIR']
    file.save(os.path.join(save_dir,file_name))
    return '/image/'+file_name

@app.route("/upload",methods={'POST','GET'})
def upload():
    file=request.files['file']
    file_ext=''
    if file.filename.find('.')>0:
        file_ext=file.filename.rsplit('.',1)[1].strip().lower()

    if file_ext in app.config['ALLOWED_EXT']:
        file_name=str(uuid.uuid1()).replace('_','')+'.'+file_ext
        url=save_to_local(file,file_name)
        if url!=None:
           db.session.add(Image(url,current_user.id))
           db.session.commit()
    return redirect('/profile/%d'%current_user.id)

