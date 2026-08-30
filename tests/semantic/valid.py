from flask import Flask, render_template, request, redirect, url_for

app = Flask(__name__)

products = [
    {"id": 1, "name": "Laptop", "price": 999.99},
    {"id": 2, "name": "Phone", "price": 699.99}
]


def format_price(price):
    return int(price)


@app.route('/')
def index():
    total = 0
    for p in products:
        total = total + p["price"]
    if total > 1000:
        print(total)
    else:
        print(0)
    return str(total)


@app.route('/add', methods=['GET', 'POST'])
def add():
    if request.method == 'POST':
        name = request.form['name']
        price = float(request.form['price'])
        return redirect(url_for('index'))
    return render_template('add_product.html')


@app.route('/product/<int:pid>')
def detail(pid):
    found = None
    for p in products:
        if p['id'] == pid:
            found = p
            break
    return render_template('product_detail.html', product=found)


if __name__ == '__main__':
    app.run(debug=True, port=8080)